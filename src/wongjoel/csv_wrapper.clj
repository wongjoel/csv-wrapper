(ns wongjoel.csv-wrapper
  (:require [clojure.string :as str])
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io])
  (:import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream))

(defn replace-blanks
  "Takes a vector of strings and replaces blank strings with unnamed-<count>"
  [v]
  (:col (->> v
            (reduce (fn [x val] (if (str/blank? val)
                                  {:count (inc (:count x))
                                   :col (conj (:col x) (str "unnamed-" (:count x)))}
                                  {:count (:count x)
                                   :col (conj (:col x) val)}))
                    {:count 0 :col []})
            )))

(defn csv-data->maps [csv-data]
  "Converts a sequence of vectors where the first vector contains the headers into a sequence of maps {:header-name row-val}"
  (map zipmap
       (->> (first csv-data)
            replace-blanks
            (map keyword)
            repeat)
       (rest csv-data)))

(defn get-reader
  "Get an appropriate reader for the filename. Throws an exception to attempt to fail fast when an unsupported filename is encountered."
  [filename]
  (cond
    (str/ends-with? filename ".csv") (-> filename
                                         io/reader)
    (str/ends-with? filename ".csv.gz") (-> filename
                                            io/input-stream
                                            GzipCompressorInputStream.
                                            io/reader)
    :else (throw (UnsupportedOperationException. (str "Unsupported file " filename)))))

(defn read-csv-file
  ""
  [filename]
  (with-open [reader (get-reader filename)]
  (doall
   (csv-data->maps (csv/read-csv reader)))))

(defn update-map
  [x f]
  (reduce-kv (fn [m k v]
               (assoc m k (f v))) {} x))

(defn parse-int-keys
  [in-map chosen-keys]
  (let [parsed-map (->> (select-keys in-map chosen-keys)
                        (reduce-kv (fn [m k v]
                                     (assoc m k (Integer/parseInt v))) {}))]
    (merge in-map parsed-map)))

(defn parse-double-keys
  [in-map chosen-keys]
  (let [parsed-map (->> (select-keys in-map chosen-keys)
                        (reduce-kv (fn [m k v]
                                     (assoc m k (Double/parseDouble v))) {}))]
    (merge in-map parsed-map)))

(defn parse-timestamp-keys
  [in-map chosen-keys time-pattern]
  (let [formatter (DateTimeFormatter/ofPattern time-pattern)
        parsed-map (->> (select-keys in-map chosen-keys)
                        (reduce-kv (fn [m k v]
                                     (assoc m k (.parse formatter v))) {}))]
    (merge in-map parsed-map)))
