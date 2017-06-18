(ns daily-dash.state
  (:require [reagent.core :as r]
            [daily-dash.constants :as constants]
            [daily-dash.utils :as utils]
            [goog.string :as gstring]
            [goog.string.format]
            [cljs-time.format :as time-format]))

(defonce state (r/atom {:bitcoin {:price {:today {:AUD 0
                                                  :USD 0}
                                          :yesterday {:AUD 0
                                                      :USD 0}
                                          :last-updated nil}}
                        :weather {:today {:temperature {:current 0
                                                        :min 0
                                                        :max 0}
                                          :description nil
                                          :wind {:speed nil
                                                 :deg nil}
                                          :humidity nil
                                          :last-updated nil
                                          :sun {:rise nil
                                                :set nil}}}}))


(defn bitcoin-price [day currency]
  (str (name currency)
       "  $"
       (gstring/format constants/price-format
         (get-in @state [:bitcoin :price day currency]))))


(defn bitcoin-price-difference [currency]
  (let [change (- (get-in @state [:bitcoin :price :today currency])
                  (get-in @state [:bitcoin :price :yesterday currency]))]
    (str "("
         (if (pos? change) "+" "")
         (gstring/format constants/price-format change)
         ")")))


(defn bitcoin-price-last-update []
  (utils/format-datetime (get-in @state [:bitcoin :price :last-updated])))


(defn weather-last-update []
  (utils/format-datetime (get-in @state [:weather :today :last-updated])))


(defn current-temperature []
  (str
    (gstring/format constants/temp-format
      (get-in @state [:weather :today :temperature :current]))
    " °c"))
