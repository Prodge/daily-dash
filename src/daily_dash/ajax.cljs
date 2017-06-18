(ns daily-dash.ajax
  (:require [ajax.core :as ajax]
            [cljs.core.async :refer [chan <! >! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn go-get!
  ([url]
    (go-get! url nil {}))

  ([url data]
    (go-get! url data {}))

  ([url data conf]
    (let [c (chan)
          default-conf {:handler (fn [res] (put! c res))
                        :error-handler (fn [res] (put! c {:error res}))
                        ;:headers {"Access-Control-Allow-Headers" "Content-Type"
								  ;"Access-Control-Allow-Origin" "*"}
                        :response-format :json
                        :params data
                        :keywords? true}]
      (go (ajax/GET url (merge default-conf conf)))
      c)))

