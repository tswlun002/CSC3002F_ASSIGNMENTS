import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OS1Assignment {

    /**
     * Open , read file and return list of virtual addresses
     * @param filename file name given by user
     * @return  virtual address in hexadecimal from file
     */
    static List<String>  readFile(String filename){
        List<String> data  = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            int  filebytes = 0;
            while ((filebytes=  file.read())!=-1){
                data.add(Integer.toHexString(filebytes));
            }
        }catch (Exception e){
            System.out.println("Can not find file "+filename);
        }
        return data;
    }

    /**
     * Re-arrange data to format of big endian
     * @param data list of virtual address
     * @return virtual addresses in big endian format
     */
    static  List<String> toBigEdian( List<String> data){
        List<String> virtual_addresses = new ArrayList<>();
        for(int  y=0; y<data.size()/8; y++){
            String address ="";
            for(int x=8*y+7; x>=8*y; x--){
                address += data.get(x);
            }
            virtual_addresses.add(address);
        }
        return  virtual_addresses;

    }

    /**
     *  Store the page table as it given on the assignment specifications
     *  Get page frame number as binary by giving value that maps to that page number
     * @param value  page number that maps to binary page frame number
     * @return page frame number
     */
    static  String  map_to(int value){

        HashMap<Integer, String> table = new HashMap<Integer,String>();

        table.put(0,"00010");
        table.put(1,"00100");
        table.put(2,"00001");
        table.put(3,"00111");
        table.put(4,"00011");
        table.put(5,"00101");
        table.put(6,"00110");

        return table.get(value);
    }

    /**
     *  converts hexadecimal number  to its 12 bits binary number
     * @param hexadecimal to be converted to binary digits
     * @return binary number of the provide hexadecimal
     */
    static  String hexToBinaryString(String hexadecimal){
        String hex ="0123456789ABCDEF";

        ArrayList<String> hex_numbers = new ArrayList<>();
        int decimal =0;
        for(String i : hex.split("")){
            hex_numbers.add(i.toUpperCase());

        }
        for(int i =0; i<hexadecimal.length();i++){
            char digit = hexadecimal.toUpperCase().charAt(i);
            int indexof_digit = hex_numbers.indexOf(digit+"");
            decimal = 16*decimal+indexof_digit;
        }

        String  bin = Integer.toBinaryString(decimal);

        if(bin.length()<12) {
            int size = 12 - bin.length();
            for (int i = 0; i < size; i++) {
                bin = "0" + bin;
            }
        }
        return bin ;
    }

    /**
     *  Converts given binary number to decimal(int) number
     * @param binvalue to be converted to decimal
     * @return  decimal value of the given binary number
     */
    static  int decimal(String  binvalue) {
        ArrayList<String> binary = new ArrayList<>();
        binary.add("0");
        binary.add("1");
        int decimal =0;
        for(int i =0; i<binvalue.length();i++){
            char digit = binvalue.toUpperCase().charAt(i);
            int indexof_digit = binary.indexOf(digit+"");
            decimal = 2*decimal+indexof_digit;

        }
        return decimal;
    }

    /**
     * Here , translation of the virtual address to physical address
     * first get store virtual address from file then convert it to its 12 bits binary number
     * Get page number get first 5 bits from virtual address then convert it decimal value which
     * will ultimately tell us the frame number by using map it to corresponding frame number
     * get offset equal last 7 bits of binary virtual address
     * Physical address in binary equal concatenation frame_number in binary & page offset in binary
     * convert binary physical address to decimal then to hexadecimal and
     * store it on list
     * @param virtual_addresses list of virtual address to translate to physical address
     * @return list of the physical address corresponds with virtual address from file
     */
    static List<String> getPhysical_address(List<String> virtual_addresses){
        List<String> physical_addresses = new ArrayList<>();
        for(String virtual_address : virtual_addresses){
            virtual_address          = hexToBinaryString(virtual_address);
            String page_table_number = virtual_address.substring(0,5);
            int page_number          = decimal(page_table_number);
            String page_frame_number = map_to(page_number);
            String offset            = virtual_address.substring(virtual_address.length()-7);
            String physical_address_inBin  = page_frame_number + offset;
            int physical_address_inDec  = decimal(physical_address_inBin);
            String  physical_address_inHex = Integer.toHexString(physical_address_inDec);

            physical_addresses.add(physical_address_inHex);
        }
        return physical_addresses;
    }
    /**
     * Convert outputs to 8 bits 
     * @param digit  number to be converted  to 8 bits
     * @param number_bit number of to convert to, in this case it is 8 bits
     * @return 8 bits number
     */
    static  String bitConversion(String digit,int number_bit){
        if(digit.length()<number_bit){
            int size = number_bit-digit.length();
            for(int i=0; i<size; i++) {
                digit = "0" + digit;
            }
        }
        return  digit;
    }

    /**
     * Write physical addresses into output text file
     * @param physical_addresses list of physical addresses to write on a text file
     * @catch  e if can't open and write a file
     */
    static  void toFile( List<String> physical_addresses){
             try{
                 FileWriter file = new FileWriter("output-OS1.txt");
                for(String physical_address : physical_addresses) {

                    file.write("0x"+bitConversion(physical_address,8).toUpperCase()+"\n");
                }
                System.out.println("Successfully wrote to the file.");
                file.close();
             }catch (IOException e){
                 System.out.println("Could not open and write into a file.");
                 e.printStackTrace();
             }
             finally {
                 System.out.println("Done.");
             }

    }

    /**
     * Get input file name
     * invoke the method to read file and store data
     * invoke method to format virtual address into big endian format
     * invoke method to do translation and get physical addresses
     * invoke to file method write data into a file
     * @param args file name
     */
    public  static  void main(String [] args){


        if(args.length ==1){

            String filename  = args[0].trim();
            List<String> data  = readFile(filename);
            List<String> virtual_addresses = toBigEdian(data);
            List<String> P_address=  getPhysical_address(virtual_addresses);
            toFile(P_address);
        }
        else{
            System.out.println("Enter correct format of the input");
        }


    }
}
