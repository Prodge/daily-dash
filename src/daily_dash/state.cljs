(ns daily-dash.state
  (:require [reagent.core :as r]
            [daily-dash.constants :as constants]
            [goog.string :as gstring]
            [goog.string.format]
            [cljs-time.format :as time-format]))

(defonce state (r/atom {:bitcoin {:price {:today {:AUD 0
                                                  :USD 0}
                                          :yesterday {:AUD 0
                                                      :USD 0}
                                          :last-updated nil}}
                        :weather {:today {:temperature {:current nil
                                                        :min nil
                                                        :max nil}
                                          :description nil
                                          :wind {:speed nil
                                                 :deg nil}
                                          :humidity nil
                                          :last-updated nil
                                          :sun {:rise nil
                                                :set nil}}}}))

(defn bitcoin-price [day currency]
  (gstring/format constants/price-format
    (get-in @state [:bitcoin :price day currency])))

(defn bitcoin-price-difference [currency]
  (gstring/format constants/price-format
          (- (bitcoin-price :today currency)
             (bitcoin-price :yesterday currency))))

(defn bitcoin-price-last-update []
  (let [last-updated (get-in @state [:bitcoin :price :last-updated])]
    (if (nil? last-updated)
      "Never"
      (time-format/unparse constants/date-time-formatter last-updated))))
