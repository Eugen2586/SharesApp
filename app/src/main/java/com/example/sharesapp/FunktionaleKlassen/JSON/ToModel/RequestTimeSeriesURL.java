package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

public class RequestTimeSeriesURL {

    //TODO Pass hier die einzelnen Einträge die im Datenmodell benötigt werden an.
    public RequestTimeSeriesURL(String s){
        /*
        [{"AccountsPayableCurrent":863051202,
        "formFiscalYear":2073,"version":"agp-usa",
        "periodStart":1569569753553,
        "periodEnd":1629485592480,
        "dateFiled":1615237666053,
        "formFiscalQuarter":3,
        "reportLink":"gv40//dsoh00cs9/.i2v22d2/20aw9A/9e0.20wtt2/09400wea5p2ha10r0stgc/r:0e/",
        "AccountsPayableRelatedPartiesCurrent":15563874,
        "AccountsReceivableNetCurrent":243965000,
        "AccountsReceivableRelatedParties":11524378,
        "AccumulatedDepreciationDepletionAndAmortizationPropertyPlantAndEquipment":108496609,
        "AccumulatedOtherComprehensiveIncomeLossNetOfTax":-4477251711,
        "AdditionalCollateralAggregateFairValue":4127481,
        "AdditionalPaidInCapitalCommonStock":2854488738,
        "AdjustmentForAmortization":20877662,
        "AllowanceForDoubtfulAccountsReceivableCurrent":28987569,
        "AlternativeRevenuePrograms":2046578,
        "AmortizationOfExcessDeferredIncomeTaxes":-10416135,
        "AmountOfAllegedImproperDistribution":738995698,
        "AssetRetirementObligation":240234694,
        "AssetRetirementObligationAccretionExpense":12437256,
        "AssetRetirementObligationCashPaidToSettle":68621905,
        "AssetRetirementObligationCurrent":95339688,
        "AssetRetirementObligationForeignCurrencyTranslationGainLoss":0,
        "AssetRetirementObligationLiabilitiesSettled":67434904,
        "AssetRetirementObligationRevisionOfEstimate":-2002102,
        "AssetRetirementObligationsNoncurrent":153014687,
        "Assets":15926802304,
        "AssetsCurrent":826408965,
        "AssetsNoncurrent":1142248052,
        "CaneRunPlantNumberOfPlaintiffsFiledSuit":6,
        "CaneRunPlantNumberOfUnresolvedCleanAirActViolations":1,
        "CanRunPlantClassOfResidentsMilesWithinPlant":4,
        "CapitalContributionsFromParent":409204282,
        "CapitalExpendituresIncurredButNotYetPaid":107593234,
        "CashAndCashEquivalentsAtCarryingValue":30793088,
        "CashAndCashEquivalentsPeriodIncreaseDecrease":6033736,
        "CashCashEquivalentsRestrictedCashAndRestrictedCashEquivalents":29275664,
        "CashCashEquivalentsRestrictedCashAndRestrictedCashEquivalentsPeriodIncreaseDecreaseIncludingExchangeRateEffect":-249177561,"CashDividendsPaidToParentCompany":-173055622,"CharitableContributions":3011757,"CollateralAlreadyPostedAggregateFairValue":0,"CommitmentsAndContingencies":0,"CommonStockAggregateSalesPrice":1001174217,"CommonStockDividendsPerShareDeclared":1.2665,"CommonStockDividendsPerShareDeclaredAsPerAnnumEquivalent":1.66,"CommonStockNoParValue":0,"CommonStockParOrStatedValuePerShare":0.01,"CommonStockSharesAuthorized":83773166,
        "CommonStockSharesIssued":39331329,
        "CommonStockSharesOutstanding":37902101,
        "CommonStockValue":313094782,
        "ComprehensiveIncomeNetOfTax":1096678672,
        "ConstructionInProgressGross":1058592577,
        "ContractWithCustomerLiability":9415428,
        "ContractWithCustomerLiabilityRevenueRecognized":9189065,
        "ContributionsMadeToLimitedLiabilityCompanyLLC":63777341,
        "CurrentCustomerDeposits":33486737,
        "DeferredIncomeTaxesAndTaxCredits":81820916,
        "DeferredInvestmentTaxCredit":130107477,
        "DeferredTaxLiabilitiesNoncurrent":1086954040,
        "DefinedBenefitPensionPlanLiabilitiesNoncurrent":269073672,
        "DefinedBenefitPlanAssetsForPlanBenefitsNoncurrent":982300669,
        "DefinedBenefitPlanNetPeriodicBenefitCostCreditNonService":238904557,
        "Depreciation":420434913,"DepreciationNotNormalized":-5169080,
        "DerivativeCollateralObligationToReturnCash":0,
        "DerivativeCollateralRightToReclaimCash":0,
        "DerivativeInstrumentsAndHedges":212001822,
        "DerivativeInstrumentsAndHedgesNoncurrent":218512601,
        "DerivativeNetLiabilityPositionAggregateFairValueAlternative":4052437,
        "DistributionMadeToLimitedLiabilityCompanyLLCMemberCashDistributionsDeclared":206030444,
        "Dividends":904501012,"DividendsCommonStockCashAndNonCash":62429251,
        "DividendsPayableCurrent":298222317,
        "EarningsPerShareBasic":1.94,
        "EarningsPerShareDiluted":1.93,
        "EffectiveIncomeTaxRateReconciliationChangeInEnactedForeignTaxRateAmount":-8359496,
        "EffectOfExchangeRateOnCashCashEquivalentsRestrictedCashAndRestrictedCashEquivalents":-10487213,
        "EntityCommonStockSharesOutstanding":37990333,"FuelCosts":341194504,
        "GainLossFromPriceRiskManagementActivity":57988508,
        "GainLossOnDiscontinuationOfInterestRateCashFlowHedgeDueToForecastedTransactionProbableOfNotOccurringNet":0,
        "GeneratingUnitsRetiredPlantOne":3,"Goodwill":1041169888,"GuaranteeObligationsCurrentCarryingValue":5071669,
        "IncomeLossFromContinuingOperationsBeforeIncomeTaxesExtraordinaryItemsNoncontrollingInterest":466340410,
        "IncomeTaxExpenseBenefit":79427926,"IncomeTaxReconciliationChangeInDeferredTaxAssetsValuationAllowance":3046775,
        "IncomeTaxReconciliationFederalTaxReformDeferredTaxCharge":0,"IncomeTaxReconciliationForeignIncomeTaxRateDifferential":-20335374,
        "IncomeTaxReconciliationForeignInterestBenefit":-9346769,
        "IncomeTaxReconciliationIncomeTaxExpenseBenefitAtFederalStatutoryIncomeTaxRate":100677855,
        "IncomeTaxReconciliationOtherAdjustments":1038424,
        "IncomeTaxReconciliationStateAndLocalIncomeTaxes":18625266,
        "IncomeTaxReconciliationStateTaxReformDeferredTaxCharge":0,
        "IncomeTaxReconciliationTaxCreditsInvestment":2004104,
        "IncomeTaxReconciliationTaxCreditsOther":5213655,
        "IncreaseDecreaseInAccountsPayableRelatedParties":6293898,
        "IncreaseDecreaseInAccountsPayableTrade":-35092296,
        "IncreaseDecreaseInAccountsReceivableRelatedParties":-9198257,
        "IncreaseDecreaseInAccruedTaxesPayable":-7046781,
        "IncreaseDecreaseInInterestPayableNet":59221306,
        "IncreaseDecreaseInInventories":-16223880,
        "IncreaseDecreaseInNotesPayableToAffiliates":16437118,
        "IncreaseDecreaseInNotesReceivableRelatedPartiesCurrent":562376464,
        "IncreaseDecreaseInOtherCurrentLiabilities":-95164516,
        "IncreaseDecreaseInOtherNoncurrentAssets":2074952,
        "IncreaseDecreaseInOtherNoncurrentLiabilities":8061840,
        "IncreaseDecreaseInOtherOperatingCapitalNet":6058317,
        "IncreaseDecreaseInPrepaidExpense":36521661,
        "IncreaseDecreaseInReceivables":-13132524,
        "IncreaseDecreaseInRegulatoryAssetsAndLiabilities":43157050,
        "IncrementalCommonSharesAttributabletoDilutiveEffectofEquityForwardSalesAgreements":8307583,
        "IncrementalCommonSharesAttributableToShareBasedPaymentArrangements":1033513,
        "IntangibleAssetExpendituresIncurredButNotYetPaid":69349658,
        "IntangibleAssetsNetExcludingGoodwill":73583843,
        "InterestExpense":177108996,"InterestExpenseRelatedParty":23820194,
        "InterestIncomeOther":1044036,"InterestIncomeRelatedParty":3014229,
        "InterestPayableCurrent":89459501,"InventoryNet":120038198,
        "LeaseCost":4067782,"LesseeOperatingLeaseLiabilityPaymentsDue":35242669,
        "LesseeOperatingLeaseLiabilityPaymentsDueAfterYearFive":3121991,"LesseeOperatingLeaseLiabilityPaymentsDueNextTwelveMonths":16300587,"LesseeOperatingLeaseLiabilityPaymentsDueYearFive":3130482,"LesseeOperatingLeaseLiabilityPaymentsDueYearFour":3007278,"LesseeOperatingLeaseLiabilityPaymentsDueYearThree":5087613,"LesseeOperatingLeaseLiabilityPaymentsDueYearTwo":7077588,"LesseeOperatingLeaseLiabilityPaymentsRemainderOfFiscalYear":3001469,"LessorOperatingLeasePaymentsToBeReceived":13190557,"LessorOperatingLeasePaymentsToBeReceivedFiveYears":0,"LessorOperatingLeasePaymentsToBeReceivedFourYears":0,"LessorOperatingLeasePaymentsToBeReceivedNextTwelveMonths":7221283,"LessorOperatingLeasePaymentsToBeReceivedRemainderOfFiscalYear":2093740,"LessorOperatingLeasePaymentsToBeReceivedThereafter":0,"LessorOperatingLeasePaymentsToBeReceivedThreeYears":0,"LessorOperatingLeasePaymentsToBeReceivedTwoYears":4124334,"LiabilitiesAndStockholdersEquity":15842778215,"LiabilitiesCurrent":1040095472,"LiabilitiesNoncurrent":3874610801,"LineOfCredit":0,"LineOfCreditFacilityAmountOfLettersOfCreditIssued":102355352,"LineOfCreditFacilityMaximumBorrowingCapacity":520977273,"LineOfCreditFacilityRemainingBorrowingCapacity":403098912,"LongTermDebtCurrent":0,"LongTermDebtNoncurrent":22276009062,"LongTermNotesAndLoans":5517770554,"MaximumAggregateCoverageBodilyInjuryAndPropertyDamage":228580377,"MaximumPercentagePaidToSellingAgents":0.02,"MembersEquity":5056916329,"NetCashProvidedByUsedInFinancingActivities":-46557023,"NetCashProvidedByUsedInInvestingActivities":-795808890,"NetCashProvidedByUsedInOperatingActivities":845158376,"NetChangeUnbilledRevenues":-4096474,"NetIncomeAvailableToCommonShareowners":1417210150,"NetIncomeLoss":401100310,"NetIncomeLossAvailableToCommonStockholdersDiluted":1385772077,"NontradeReceivablesCurrent":27598835,"NotesPayableRelatedPartiesClassifiedCurrent":132266936,"NotesPayableRelatedPartiesNoncurrent":653502117,"NotesReceivableRelatedPartiesCurrent":552230043,"NumberOfOperatingSegments":1,"NumberOfReportableSegments":1,"OperatingIncomeLoss":671714533,"OperatingLeaseCost":3070705,"OperatingLeaseLeaseIncome":10309963,"OperatingLeaseLiabilityCurrent":9380576,"OperatingLeaseLiabilityNoncurrent":22724931,"OperatingLeasePayments":9260889,"OperatingLeaseRightOfUseAsset":27377831,"OperatingLeasesFutureMinimumPaymentsDue":72523261,"OperatingLeasesFutureMinimumPaymentsDueCurrent":20422129,"OperatingLeasesFutureMinimumPaymentsDueInFiveYears":6271784,"OperatingLeasesFutureMinimumPaymentsDueInFourYears":7176276,"OperatingLeasesFutureMinimumPaymentsDueInThreeYears":11455020,"OperatingLeasesFutureMinimumPaymentsDueInTwoYears":15706119,"OperatingLeasesFutureMinimumPaymentsDueThereafter":11003769,"OperatingLeaseWeightedAverageDiscountRatePercent":0.0417,"OtherAssetsCurrent":10169764,"OtherAssetsNoncurrent":335921829,"OtherComprehensiveIncomeDefinedBenefitPlanNetPriorServiceCostsCreditArisingDuringPeriodNetOfTax":0,"OtherComprehensiveIncomeDefinedBenefitPlansNetUnamortizedGainLossArisingDuringPeriodNetOfTax":-10338639,"OtherComprehensiveIncomeDefinedBenefitPlansNetUnamortizedGainLossArisingDuringPeriodTax":-4127399,"OtherComprehensiveIncomeForeignCurrencyTransactionAndTranslationGainLossArisingDuringPeriodNetOfTax":-371001078,"OtherComprehensiveIncomeForeignCurrencyTranslationGainLossArisingDuringPeriodTax":0,"OtherComprehensiveIncomeLossAmortizationAdjustmentFromAOCIPensionAndOtherPostretirementBenefitPlansForNetPriorServiceCostCreditNetOfTax":1049526,"OtherComprehensiveIncomeLossAmortizationAdjustmentFromAOCIPensionAndOtherPostretirementBenefitPlansForNetPriorServiceCostCreditTax":1037932,"OtherComprehensiveIncomeLossBeforeReclassificationsNetOfTax":-348591967,"OtherComprehensiveIncomeLossCashFlowHedgeGainLossBeforeReclassificationAfterTax":32719438,"OtherComprehensiveIncomeLossCashFlowHedgeGainLossBeforeReclassificationTax":7168793,"OtherComprehensiveIncomeLossCashFlowHedgeGainLossReclassificationAfterTax":25438476,"OtherComprehensiveIncomeLossCashFlowHedgeGainLossReclassificationTax":3095589,"OtherComprehensiveIncomeLossNetOfTax":-308271832,"OtherComprehensiveIncomeLossPensionAndOtherPostretirementBenefitPlansBenefitPlanImprovementTaxEffect":0,"OtherComprehensiveIncomeLossReclassificationAdjustmentFromAOCIPensionAndOtherPostretirementBenefitPlansForNetGainLossNetOfTax":-62970458,"OtherComprehensiveIncomeLossReclassificationAdjustmentFromAOCIPensionAndOtherPostretirementBenefitPlansForNetGainLossTax":-16622261,"OtherLiabilitiesCurrent":132222429,"OtherLiabilitiesNoncurrent":154125968,"OtherNoncashIncomeExpense":3004920,"OtherNonoperatingExpense":19070750,"OtherNonoperatingExpenseMiscellaneous":16164860,"OtherNonoperatingIncome":21650670,"OtherNonoperatingIncomeExpense":2032576,"OtherNonoperatingIncomeMiscellaneous":6122846,"OtherRevenue":-8012196,"PaymentsForProceedsFromOtherInvestingActivities":0,"PaymentsOfDistributionsToAffiliates":207706792,"PaymentsOfDividendsCommonStock":924447514,"PaymentsToAcquireIntangibleAssets":4133164,"PaymentsToAcquireInvestments":55315241,"PaymentsToAcquirePropertyPlantAndEquipment":761172380,"PensionAndOtherPostretirementBenefitsExpenseReversalOfExpenseNoncash":-204299507,"PensionContributions":3129639,"PrepaidExpenseCurrent":31896981,"PriceRiskManagementLiabilities":20230409,"PriceRiskManagementLiabilitiesCurrent":5140695,"ProceedsFromContributionsFromAffiliates":63230744,"ProceedsFromContributionsFromParent":71226508,"ProceedsFromIssuanceOfCommonStock":49924713,"ProceedsFromIssuanceOfLongTermDebt":718802043,"ProceedsFromPaymentsForOtherFinancingActivities":-11076510,"ProceedsFromPollutionControlBond1":40795368,"ProceedsFromRelatedPartyDebt":0,"ProceedsFromRepaymentsOfShortTermDebt":-432951176,"ProceedsFromSaleMaturityAndCollectionsOfInvestments":65183653,"Proceedsfromtransferofexcessbenefitfunding":0,"PropertyPlantAndEquipmentNet":13489974607,"PropertyPlantAndEquipmentNetNonregulated":228031562,"PropertyPlantAndEquipmentOther":334389982,"ProvisionForDoubtfulAccounts":22811682,"PublicUtilitiesAllowanceForFundsUsedDuringConstructionCapitalizedCostOfEquity":17390416,"PublicUtilitiesPropertyPlantAndEquipmentAccumulatedDepreciation":1539826868,"PublicUtilitiesPropertyPlantAndEquipmentNet":34469678149,"PublicUtilitiesPropertyPlantAndEquipmentPlantInService":6303423339,"PurchasedPowerAndGas":539014020,"ReclassificationFromAccumulatedOtherComprehensiveIncomeCurrentPeriodNetOfTax":-2076293,"RegulatedOperatingRevenue":1136588226,"RegulatoryAssetsCurrent":50758485,"RegulatoryAssetsNoncurrent":1686934739,"RegulatoryLiabilityCurrent":19741602,"RegulatoryLiabilityNoncurrent":2709844065,"RepaymentsOfLongTermDebt":206482026,"RepaymentsofPollutionControlBond1":40595556,"RestrictedCashCurrent":2015100,"RestrictedCashNoncurrent":0,"RestructuringAndRelatedActivitiesOwnershipPercentageByRiverstone":0.36,"RetainedEarningsAccumulatedDeficit":560496394,"RevenueFromContractWithCustomerExcludingAssessedTax":1324117910,"RevenueFromRelatedParties":6296179,"Revenues":2426534583,"RightOfUseAssetObtainedInExchangeForOperatingLeaseLiability":5070773,"ShareBasedCompensation":24740375,"ShortTermBorrowings":101663848,"ShortTermLeaseCost":1024321,"StockBasedCompensation":5090070,"StockholdersEquity":3735303567,"StockIssuedDuringPeriodSharesDividendReinvestmentPlan":1364513,"StockIssuedDuringPeriodSharesNewIssues":0,"StockIssuedDuringPeriodSharesShareBasedCompensation":696422,"StockIssuedDuringPeriodValueNewIssues":63593779,"TaxesPayableCurrent":57936926,"TotalIncreaseDecreaseToFederalIncomeTaxOnIncomeFromContinuingOperationsAtStatutoryTaxRate":-20523444,"UnbilledContractsReceivable":74016399,"UndistributedEarningsLossAllocatedToParticipatingSecuritiesBasic":2098027,"Unrealizedgainsonderivativesandotherhedgingactivities":18681348,"UtilitiesOperatingExpense":3704544493,"UtilitiesOperatingExpenseDepreciationAndAmortization":236397823,"UtilitiesOperatingExpenseMaintenanceOperationsAndOtherCostsAndExpenses":1495373569,"UtilitiesOperatingExpensePurchasedPowerFromRelatedParties":21458194,"UtilitiesOperatingExpenseTaxes":26479086,"VotesPerShareOfCommonStock":1,"WeightedAverageNumberOfDilutedSharesOutstanding":734362801,"WeightedAverageNumberOfSharesOutstandingBasic":744421278,"id":"DPELRO_ACNERSITIFNA","source":"SEC","key":"LPP",
        "subkey":"0Q-1","updated":1610887222238}


        Alle attribute, die du hier findest kannst du dann similar Request Symbol mappen.
         */




    }

    /*getTimeSeriesURL(){

    }*/




}
