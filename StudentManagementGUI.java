/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.StudentDAO;
import model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {
    
    private StudentDAO studentDAO;
    
    // Components
    private JTextField txtRollNumber, txtName, txtEmail, txtPhone, txtCourse, txtYear, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch, btnRefresh;
    
    public StudentManagementGUI() {
        studentDAO = new StudentDAO();
        initComponents();
        loadStudentData();
    }
    
    private void initComponents() {
        setTitle("Student Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Input Panel
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        
        // Table Panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        panel.setBackground(new Color(240, 240, 240));
        
        // Roll Number
        panel.add(new JLabel("Roll Number:"));
        txtRollNumber = new JTextField();
        panel.add(txtRollNumber);
        
        // Name
        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);
        
        // Email
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);
        
        // Phone
        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);
        
        // Course
        panel.add(new JLabel("Course:"));
        txtCourse = new JTextField();
        panel.add(txtCourse);
        
        // Year
        panel.add(new JLabel("Year:"));
        txtYear = new JTextField();
        panel.add(txtYear);
        
        // Search
        panel.add(new JLabel("Search by Name:"));
        txtSearch = new JTextField();
        panel.add(txtSearch);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Student Records"));
        
        // Create table
        String[] columnNames = {"ID", "Roll Number", "Name", "Email", "Phone", "Course", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Add mouse listener to fill form when row is clicked
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtRollNumber.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtPhone.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtCourse.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    txtYear.setText(tableModel.getValueAt(selectedRow, 6).toString());
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        btnAdd = new JButton("Add Student");
        btnUpdate = new JButton("Update Student");
        btnDelete = new JButton("Delete Student");
        btnClear = new JButton("Clear Fields");
        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh");
        
        // Set button colors
        btnAdd.setBackground(new Color(76, 175, 80));
        btnUpdate.setBackground(new Color(33, 150, 243));
        btnDelete.setBackground(new Color(244, 67, 54));
        btnClear.setBackground(new Color(158, 158, 158));
        btnSearch.setBackground(new Color(255, 193, 7));
        btnRefresh.setBackground(new Color(156, 39, 176));
        
        // Set foreground color
        btnAdd.setForeground(Color.WHITE);
        btnUpdate.setForeground(Color.WHITE);
        btnDelete.setForeground(Color.WHITE);
        btnClear.setForeground(Color.WHITE);
        btnSearch.setForeground(Color.BLACK);
        btnRefresh.setForeground(Color.WHITE);
        
        // Add action listeners
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnSearch.addActionListener(e -> searchStudents());
        btnRefresh.addActionListener(e -> loadStudentData());
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnSearch);
        panel.add(btnRefresh);
        
        return panel;
    }
    
    private void addStudent() {
        if (validateInputs()) {
            Student student = new Student(
                txtRollNumber.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtCourse.getText(),
                Integer.parseInt(txtYear.getText())
            );
            
            if (studentDAO.addStudent(student)) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                clearFields();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student. Roll number might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateStudent() {
        if (validateInputs()) {
            Student student = new Student(
                txtRollNumber.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtCourse.getText(),
                Integer.parseInt(txtYear.getText())
            );
            
            if (studentDAO.updateStudent(student)) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
                clearFields();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteStudent() {
        String rollNumber = txtRollNumber.getText().trim();
        
        if (rollNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter roll number to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.deleteStudent(rollNumber)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                clearFields();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchStudents() {
        String searchName = txtSearch.getText().trim();
        
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Student> students = studentDAO.searchStudentsByName(searchName);
        displayStudents(students);
        
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found with that name.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadStudentData() {
        List<Student> students = studentDAO.getAllStudents();
        displayStudents(students);
        txtSearch.setText("");
    }
    
    private void displayStudents(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            Object[] row = {
                student.getId(),
                student.getRollNumber(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getCourse(),
                student.getYear()
            };
            tableModel.addRow(row);
        }
    }
    
    private void clearFields() {
        txtRollNumber.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtCourse.setText("");
        txtYear.setText("");
        table.clearSelection();
    }
    
    private boolean validateInputs() {
        if (txtRollNumber.getText().trim().isEmpty() ||
            txtName.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtPhone.getText().trim().isEmpty() ||
            txtCourse.getText().trim().isEmpty() ||
            txtYear.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "All fields are required!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            Integer.parseInt(txtYear.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a number!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentManagementGUI().setVisible(true);
        });
    }
}
