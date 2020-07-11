# csv-wrapper

Convenience methods based on [clojure/data.csv](https://github.com/clojure/data.csv) for reading CSV files. This is more of an example of how to build on the foundation given by [clojure/data.csv](https://github.com/clojure/data.csv) than a full featured library. For a more full featured csv library, check out [metasoarous/semantic-csv](https://github.com/metasoarous/semantic-csv).

## Why?

- You want to read a csv file into clojure, and found [clojure/data.csv](https://github.com/clojure/data.csv) to be too low level to suit your needs
- Instead of a sequence of vectors of string values, you want a map of `{:column-name row-val}`
- Your csv files have come from pandas and has an added unnamed index column, and you want to have any blank column names automatically replaced with automatically numbered `"unnamed-0"`
- You want to read a compressed `csv.gz` file by just passing in a string filename, without bothering with `GZipInputStream`
- You want to parse the row values from string to a specified type

If these use cases match exactly what you want, this library might be what you're looking for. If not, you might be able to borrow some ideas from this library to roll your own solution.

## Why not?

- Your requirements are different to what this repo was written for
- Your requirements are met better by some other library (e.g. [metasoarous/semantic-csv](https://github.com/metasoarous/semantic-csv))
- You end up writing your own code to meet your requirements exactly
- etc.

## Usage

Add the library to your `deps.edn` file.

```clojure
{:deps
 {wongjoel/csv-wrapper
  {:git/url "https://github.com/wongjoel/csv-wrapper.git"
   :sha ""}}}
```

Example usage

```clojure

```

## License

Copyright © 2020 Joel Wong

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
