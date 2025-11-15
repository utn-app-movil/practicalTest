package Entity

class Receipt {
    private var emitter_name: String = ""
    private var receiver_name: String = ""
    private var receiver_fiscal_id: String = ""
    private var emitter_fiscal_id: String = ""
    private var receipt_id: String = ""
    private var receipt_description: String = ""
    private var tax_rate: Double = 0.0

    constructor(emitter_name: String, receiver_name: String, receiver_fiscal_id: String, emitter_fiscal_id: String, receipt_id: String, receipt_description: String, tax_rate: Double){
        this.emitter_name = emitter_name
        this.receiver_name = receiver_name
        this.receiver_fiscal_id = receiver_fiscal_id
        this.emitter_fiscal_id = emitter_fiscal_id
        this.receipt_id = receipt_id
        this.receipt_description = receipt_description
        this.tax_rate = tax_rate
    }

    constructor(){}

    // ----- Propiedades tipo Getter/Setter estilo Kotlin -----
    var EmitterName: String
        get() = this.emitter_name
        set(value) { this.emitter_name = value }

    var ReceiverName: String
        get() = this.receiver_name
        set(value) { this.receiver_name = value }

    var ReceiverFiscalId: String
        get() = this.receiver_fiscal_id
        set(value) { this.receiver_fiscal_id = value }

    var EmitterFiscalId: String
        get() = this.emitter_fiscal_id
        set(value) { this.emitter_fiscal_id = value }

    var ReceiptId: String
        get() = this.receipt_id
        set(value) { this.receipt_id = value }

    var ReceiptDescription: String
        get() = this.receipt_description
        set(value) { this.receipt_description = value }

    var TaxRate: Double
        get() = this.tax_rate
        set(value) { this.tax_rate = value }
}