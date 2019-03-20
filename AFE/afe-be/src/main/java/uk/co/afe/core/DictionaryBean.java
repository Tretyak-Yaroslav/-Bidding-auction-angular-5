package uk.co.afe.core;

import jdk.nashorn.internal.ir.WhileNode;
import lombok.experimental.var;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.Platform;
import uk.co.afe.model.exceptions.BaseRuntimeException;
import uk.co.afe.model.exceptions.DictionaryIsEmptyException;
import uk.co.afe.model.exceptions.ObjectNotFoundException;
import uk.co.afe.model.questionaries.Questionnaire;
import uk.co.afe.model.questionaries.QuestionnaireEntity;
import uk.co.afe.persistance.service.DaoPlatform;
import uk.co.afe.persistance.service.DaoQuestionnaire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 15:08
 */
@Service
public class DictionaryBean {

    @Autowired
    private DaoPlatform daoPlatform;
    @Autowired
    private DaoQuestionnaire daoQuestionnaire;

    public List<Platform> getAllPlatforms() {
        return daoPlatform.findAll();
    }

    public Questionnaire getCurrentQuestionnaire() {
        List<QuestionnaireEntity> list = daoQuestionnaire.findAll();
        QuestionnaireEntity current;
        if (list.isEmpty()) {
            throw new DictionaryIsEmptyException(ErrorCode.DICT_OF_QUESTIONS_IS_EMPTY, "Questionnaire");
        }
        if (list.size() == 1) {
            current = list.get(0);
        } else {
            list.sort((q1, q2) -> (int) (q2.getQuestionnaireId() - q1.getQuestionnaireId()));
            current = list.get(0);
        }
        Questionnaire questionnaire = current.getQuestionnaire();
        questionnaire.setId(current.getQuestionnaireId());
        return questionnaire;
    }

    public Questionnaire getQuestionnaireById(Long id) {
        return daoQuestionnaire.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(ErrorCode.QUESTIONNAIRE_NOT_FOUND, id, Questionnaire.class)
        ).getQuestionnaire();
    }

    public void uploadList(byte[] file) {
        List<Platform> newPlatforms = new LinkedList<>();
        try {
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                Long id = Double.valueOf(row.getCell(0).getNumericCellValue()).longValue();
                String name = row.getCell(1).getStringCellValue();
                Boolean hidden = Boolean.valueOf(row.getCell(2).getStringCellValue());
                Platform p = new Platform();
                p.setPlatformId(id);
                p.setName(name);
                p.setHidden(hidden);
                newPlatforms.add(p);
            }
        } catch (Exception e) {
            throw new BaseRuntimeException(ErrorCode.UNKNOWN_ERROR, "Error while converting byte array to table", e);
        }
        List<Platform> platforms = getAllPlatforms();
        Iterator<Platform> iterator = newPlatforms.iterator();
        while(iterator.hasNext()) {
            Platform n = iterator.next();
            for (Platform p : platforms) {
                if (p.getPlatformId().equals(n.getPlatformId())) {
                    p.setName(n.getName());
                    p.setHidden(n.getHidden());
                    daoPlatform.saveAndFlush(p);
                    iterator.remove();
                }
            }
        }
        for (Platform p : newPlatforms) {
            p.setPlatformId(null);
            daoPlatform.saveAndFlush(p);
        }
    }

    public byte[] downloadPlatforms() {
        List<Platform> platforms = getAllPlatforms();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("platformId");
            title.createCell(1).setCellValue("name");
            title.createCell(2).setCellValue("hidden");
            for (int i = 0; i < platforms.size(); i++) {
                Platform p = platforms.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(p.getPlatformId());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(String.valueOf(p.getHidden()));
            }
            workbook.write(os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new BaseRuntimeException(ErrorCode.UNKNOWN_ERROR, "Error while converting table to byte array", e);
        }
    }

}
