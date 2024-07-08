package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.PdaSectores;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.repository.PdaSectoresRepository;
import pe.sernanp.ws_api.service.PdaSectoresService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdaSectoresServiceImpl extends BaseServiceImpl implements PdaSectoresService{

    @Autowired
    PdaSectoresRepository _repository;

    public ResponseEntity<PdaSectores> listByAnp(int idAnp) throws Exception {
        ResponseEntity<PdaSectores> response = new ResponseEntity<PdaSectores>();
        try {
            List<PdaSectores> items = _repository.listByIdAnp(idAnp);

            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<PdaSectores> listByAnps(String idsAnp) throws Exception {
        ResponseEntity<PdaSectores> response = new ResponseEntity<>();
        try {
            if (isNullOrEmpty(idsAnp)) {
                response.setMessage("Debe enviar al menos un ANP.");
                response.setSuccess(false);
            } else {
                String[] anpIds = Arrays.stream(idsAnp.split(",")).toArray(String[]::new);
                List<PdaSectores> items = _repository.listByAnpIds(anpIds);

                List<PdaSectores> itemsResult = items.stream().map(t -> {
                    PdaSectores temp = new PdaSectores();
                    temp.setSector(t.getSector());
                    temp.setActivity(t.getActivity());
                    temp.setAnpName(t.getAnpName());
                    temp.setAnpCode(t.getAnpCode());
                    temp.setAnpId(t.getAnpId());

                    t.setSectorId(t.getSector().getId());
                    t.setAnpId(0);
                    t.setAnpName(null);
                    t.setActivity(null);
                    t.setSector(null);
                    return temp;
                }).distinct().collect(Collectors.toList());

                itemsResult = itemsResult.stream().map(t -> {
                    t.setAnios(items.stream().filter(t2 -> t2.getAnpCode().equalsIgnoreCase(t.getAnpCode()) && t2.getSectorId() == t.getSector().getId()).collect(Collectors.toList()));
                    return t;
                }).collect(Collectors.toList());

                response.setItems(itemsResult);
            }

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(String idsAnp) throws Exception {
        String year = Integer.toString(Year.now().getValue());
        String year1 = Integer.toString(Year.now().getValue()+1);
        String year2 = Integer.toString(Year.now().getValue()+2);
        String year3 = Integer.toString(Year.now().getValue()+3);
        String[] columns = { "ACTIVIDAD TURISTICA", "SECTOR TURISTICO", year, year1, year2, year3 };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Hoja 1");
        Row row = sheet.createRow(0);
        Row row2 = sheet.createRow(1);

        row.setRowStyle(workbook.createCellStyle());
        row2.setRowStyle(workbook.createCellStyle());
        row.getRowStyle().setWrapText(true);
        row.getRowStyle().setAlignment(HorizontalAlignment.CENTER);
        row.getRowStyle().setVerticalAlignment(VerticalAlignment.CENTER);

        for (int i = 0; i < columns.length; i++) {
            if (i < 2) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(row.getRowStyle());
            }
            else {
                Cell cell2 = row2.createCell(i);
                cell2.setCellValue(columns[i]);
                cell2.setCellStyle(row.getRowStyle());;
            }
        }
        Cell cell = row.createCell(2);
        cell.setCellValue("AÑOS");
        cell.setCellStyle(row.getRowStyle());

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C1:F1"));

        String[] anpIds = Arrays.stream(idsAnp.split(",")).toArray(String[]::new);
        List<PdaSectores> items = _repository.listByAnpIds(anpIds);

        List<PdaSectores> itemsResult = items.stream().map(t -> {
            PdaSectores temp = new PdaSectores();
            temp.setSector(t.getSector());
            temp.setActivity(t.getActivity());
            temp.setAnpName(t.getAnpName());
            temp.setAnpCode(t.getAnpCode());
            temp.setAnpId(t.getAnpId());

            t.setSectorId(t.getSector().getId());
            t.setAnpId(0);
            t.setAnpName(null);
            t.setActivity(null);
            t.setSector(null);
            return temp;
        }).distinct().collect(Collectors.toList());

        itemsResult = itemsResult.stream().map(t -> {
            t.setAnios(items.stream().filter(t2 -> t2.getAnpCode().equalsIgnoreCase(t.getAnpCode()) && t2.getSectorId() == t.getSector().getId()).collect(Collectors.toList()));
            return t;
        }).collect(Collectors.toList());

        int initRow = 2;
        int initRow2;
        for (PdaSectores _item : itemsResult) {
            row = sheet.createRow(initRow);

            row.createCell(0).setCellValue(_item.getActivity() == null ? "" : _item.getActivity().getName());
            row.createCell(1).setCellValue(_item.getSector() == null ? "" : _item.getSector().getName());
            initRow2 = 2;
            for (PdaSectores _item2 : _item.getAnios()) {
                if (_item2.getAnio() == Integer.parseInt(year))
                    row.createCell(initRow2).setCellValue(_item2.getValue());
                else if (_item2.getAnio() == Integer.parseInt(year1))
                    row.createCell(initRow2).setCellValue(_item2.getValue());
                else if (_item2.getAnio() == Integer.parseInt(year2))
                    row.createCell(initRow2).setCellValue(_item2.getValue());
                else if (_item2.getAnio() == Integer.parseInt(year3))
                    row.createCell(initRow2).setCellValue(_item2.getValue());
                else
                    row.createCell(initRow2).setCellValue(0);
                initRow2++;
            }
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
