(ns daily-dash.utils)

(defn error? [response]
  (contains? response :error))
