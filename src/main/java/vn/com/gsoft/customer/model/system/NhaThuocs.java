package vn.com.gsoft.customer.model.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NhaThuocs {
    private String maNhaThuoc;
    private String tenNhaThuoc;
    private String diaChi;
    private String soKinhDoanh;
    private String dienThoai;
    private String nguoiDaiDien;
    private String email;
    private String mobile;
    private String duocSy;
    private Date created;
    private Date modified;
    private Integer createdByUserId;
    private Integer modifiedByUserId;
    private Boolean hoatDong;
    private Boolean active;
    private Boolean isReportDataGenerating;
    private Integer tinhThanhId;
    private String maNhaThuocCha;
    private Integer id;
    private Boolean isConnectivity;
    private String connectivityCode;
    private String connectivityUserName;
    private String connectivityPassword;
    private String generalPharmacyId;
    private Boolean isGeneralPharmacy;
    private BigDecimal paidAmount;
    private String description;
    private Integer drugStoreTypeId;
    private Boolean isPaid;
    private Integer chainLinkId;
    private Integer regionId;
    private Integer cityId;
    private Integer wardId;
    private Date lastTransDate;
    private String supportPhones;
    private String deliveryPolicy;
    private String contentThankYou;
    private Integer recordStatusId;
    private Integer idTypeBasic;
    private String ghiChu;
    private Boolean isNationalDBConnected;
    private String imagePreviewUrl;
    private String imageThumbUrl;
    private Integer totalNumberInvoices;
    private String connEInvoiceUserName;
    private String connEInvoicePassword;
    private String symbolCodeInvocie;
    private String formNumberInvoice;
    private Integer typeInvoice;
    private Boolean paymentStatus;
    private BigDecimal paidMoney;
    private Boolean isNationalSampleConnected;
    private String connectivityCodeMeidcal;
    private String connectivityPasswordMedical;
    private Date expiredDate;
    private String businessDescription;
    private Date paidDate;
    private Boolean isUploading;
    private String connEInvoiceSerialCert;
    private String linkConnectEInvoice;
    private String nameServiceEInvoice;
    private String passServiceEInvoice;
    private String signedString;
    private Integer typeSendEinvocie;
    private String slugCustomerWebsite;
    private String googleLocationCustomerWebsite;
    private Integer themeIdCustomerWebsite;
    private String tokenZalo;
    private String zaloKey;
    private String appId;
    private String refreshTokenZalo;
    private String imageOrderThumbUrl;
    private String imageOrderPreviewUrl;
    private Integer typeMessage;
    private String bannerCustomerWebsite;
    private String mainSloganCustomerWebsite;
    private String subSloganCustomerWebsite;
    private String qRDeviceName;
    private String qRDeviceToken;
    private String simData;
    private Boolean mapped;
    private Boolean zNSStatusSendCreateAccount;
    private String zNSTrackingIdCreateAccount;
    private Boolean zNSStatusSendPayment;
    private String zNSTrackingIdPayment;
    private Integer businessId;
    private Integer codeErrorConfirmPaymentZNS;
    private Integer codeErrorCreateAccountZNS;
    private String footerPrint;
    private Integer classify;
    private Integer evaluate;
    private Integer supporterId;
    private Boolean upgradeToPlus;
}

