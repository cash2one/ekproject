/* Formatted on 2010/11/16 12:03 (Formatter Plus v4.8.8) */
SELECT TO_CHAR
          (DECODE
              (SIGN
                  (  (  TO_DATE ('2010-11-16', 'YYYY-MM-DD')
                      + TO_NUMBER
                            (DECODE (TO_CHAR (TRUNC (TO_DATE ('2010-11-16',
                                                              'YYYY-MM-DD'
                                                             ),
                                                     'YYYY'
                                                    ),
                                              'D'
                                             ),
                                     '1', '8',
                                     TO_CHAR (TRUNC (TO_DATE ('2010-11-16',
                                                              'YYYY-MM-DD'
                                                             ),
                                                     'YYYY'
                                                    ),
                                              'D'
                                             )
                                    )
                            )
                      - 2
                     )
                   - LAST_DAY (TO_DATE ('2010-11-16', 'YYYY-MM-DD'))
                  ),
               1, LAST_DAY (TO_DATE ('2010-11-16', 'YYYY-MM-DD')),
               (  TO_DATE ('2010-11-16', 'YYYY-MM-DD')
                + TO_NUMBER (DECODE (TO_CHAR (TRUNC (TO_DATE ('2010-11-16',
                                                              'YYYY-MM-DD'
                                                             ),
                                                     'YYYY'
                                                    ),
                                              'D'
                                             ),
                                     '1', '8',
                                     TO_CHAR (TRUNC (TO_DATE ('2010-11-16',
                                                              'YYYY-MM-DD'
                                                             ),
                                                     'YYYY'
                                                    ),
                                              'D'
                                             )
                                    )
                            )
                - 2
               )
              ),
           'WW'
          )
  FROM DUAL;