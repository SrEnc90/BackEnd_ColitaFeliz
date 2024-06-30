package com.cibertec.colitafeliz.service.implement;

import com.cibertec.colitafeliz.JWT.JwtFilter;
import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.dao.BillDao;
import com.cibertec.colitafeliz.entities.BillEntity;
import com.cibertec.colitafeliz.service.BillService;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside generateReport");
        try {
            String fileName;
            if(validateRequestMap(requestMap)) {
                if(requestMap.containsKey("isGenerate") && !(boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = GlobalUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }

                String data = "Razón Social: " + requestMap.get("razonSocial") + "\n" +
                        "Teléfono: " + requestMap.get("contactNumber") + "\n" +
                        "Email: " + requestMap.get("email") + "\n" +
                        "Dirección: " + requestMap.get("address") + "\n" +
                        "Método de pago: " + requestMap.get("paymentMethod") + "\n";

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream( GlobalConstants.STORE_LOCATION + fileName + ".pdf"));
                document.open();
                setRectangleInPdf(document);

                Paragraph chunk = new Paragraph("Sistema Colita Feliz", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data  + "\n\n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = GlobalUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, GlobalUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: " + requestMap.get("totalAmount") +
                        "\n" + "Gracias por visitarnos, Por favor vuelva pronto!!!", getFont("Data"));
                document.add(footer);

                document.close();

                return GlobalUtils.getResponseEntity(GlobalConstants.BILL_GENERATED + fileName, HttpStatus.OK);


            }
            return GlobalUtils.getResponseEntity(GlobalConstants.BILL_EMPTY, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double)data.get("price")));
        table.addCell(Double.toString((Double)data.get("subTotal")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Producto", "Categoría", "Cantidad", "Precio", "Sub total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GREEN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type) {
            case "Header" :
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case"Data" :
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }

    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap != null
                && requestMap.containsKey("razonSocial") && requestMap.get("razonSocial") != null
                && requestMap.containsKey("email") && requestMap.get("email") != null
                && requestMap.containsKey("contactNumber") && requestMap.get("contactNumber") != null
                && requestMap.containsKey("address") && requestMap.get("address") != null
                && requestMap.containsKey("paymentMethod") && requestMap.get("paymentMethod") != null
                && requestMap.containsKey("totalAmount") && requestMap.get("totalAmount") != null
                && requestMap.containsKey("productDetails") && requestMap.get("productDetails") != null
                && requestMap.containsKey("createdBy") && requestMap.get("createdBy") != null;
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            BillEntity bill = BillEntity.builder()
                    .uuid((String) requestMap.get("uuid"))
                    .razonSocial((String) requestMap.get("razonSocial"))
                    .email((String) requestMap.get("email"))
                    .contactNumber((String) requestMap.get("contactNumber"))
                    .address((String) requestMap.get("address"))
                    .paymentMethod((String) requestMap.get("paymentMethod"))
                    .totalAmount((Double) requestMap.get("totalAmount"))
                    .productDetails((String) requestMap.get("productDetails"))
                    .createdBy(JwtFilter.getCurrentUser())
                    .build();

            billDao.save(bill);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBackgroundColor(BaseColor.BLACK);
        rect.setBorderWidth(2);
        document.add(rect);
    }
}
