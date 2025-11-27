package infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;

public class GenericCsvExporter {
    
    public <T> String exportToCSV(List<T> data, List<String> selectedFields) {
        if (data == null || data.isEmpty()) {
            return "Nenhum dado para exportar";
        }
        
        StringBuilder csv = new StringBuilder();
        
        // Cabeçalho
        csv.append(String.join(",", selectedFields));
        csv.append("\n");
        
        // Dados
        for (T item : data) {
            StringBuilder row = new StringBuilder();
            for (int i = 0; i < selectedFields.size(); i++) {
                String fieldName = selectedFields.get(i);
                String value = getFieldValue(item, fieldName);
                row.append(value);
                
                if (i < selectedFields.size() - 1) {
                    row.append(",");
                }
            }
            csv.append(row.toString());
            csv.append("\n");
        }
        
        return csv.toString();
    }
    
    private <T> String getFieldValue(T object, String fieldName) {
        try {
            Field field = findField(object.getClass(), fieldName);
            if (field == null) {
                return "N/A";
            }
            field.setAccessible(true);
            Object value = field.get(object);
            return value != null ? value.toString() : "";
        } catch (IllegalAccessException e) {
            return "ERROR";
        }
    }
    
    private Field findField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Tentar na superclasse
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            }
            return null;
        }
    }
}
