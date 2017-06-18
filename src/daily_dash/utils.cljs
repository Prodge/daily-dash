(ns daily-dash.utils
  (:require [daily-dash.constants :as constants]
            [cljs-time.format :as time-format]))

(defn error? [response]
  (contains? response :error))

(defn format-datetime [datetime]
  (if (nil? datetime)
    "Never"
    (time-format/unparse constants/date-time-formatter datetime)))
