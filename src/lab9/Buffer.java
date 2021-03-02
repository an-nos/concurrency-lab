package lab9;

import org.jcsp.lang.*;

/**
 * Buffer class: Manages communication between Producer2
 * and Consumer2 classes.
 */
public class Buffer implements CSProcess {
    private One2OneChannelInt[] in; // Input from Producer
    private One2OneChannelInt[] req; // Request for data from Consumer
    private One2OneChannelInt[] out; // Output to Consumer
    // The buffer itself
    private int[] buffer = new int[10];
    // Subscripts for buffer
    int hd = -1;
    int tl = -1;

    public Buffer(final One2OneChannelInt[] in, final
    One2OneChannelInt[] req, final One2OneChannelInt[] out) {
        this.in = in;
        this.req = req;
        this.out = out;
    } // constructor

    public void run() {
//        final Guard[] guards = {in[0], in[1], req[0], req[1]};
//        Guard guard = in[0].in();

        final Guard[] guards = {in[0].in(), in[1].in(), req[0].in(), req[1].in()};

        final Alternative alt = new Alternative(guards);
        int countdown = 4; // Number of processes running
        while (countdown > 0) {
            int index = alt.select();
            switch (index) {
                case 0:
                case 1: // A Producer is ready to send
                    if (hd < tl + 11) // Space available
                    {
                        ChannelInputInt channelInputInt = in[index].in();
                        int item = channelInputInt.read();
//                        int item = in[index].read();
                        if (item < 0)
                            countdown--;
                        else {
                            hd++;
                            buffer[hd % buffer.length] = item;
                        }
                    }
                    break;
                case 2:
                case 3: // A Consumer is ready to read
                    ChannelInputInt channelInputInt2 = req[index - 2].in();
                    ChannelOutputInt channelOutputInt2 = out[index - 2].out();
                    if (tl < hd) // Item(s) available
                    {
                        channelInputInt2.read();
//                        req[index - 2].read(); // Read and discard request
                        tl++;
                        int item = buffer[tl % buffer.length];  // consumer is waiting for reading
                        channelOutputInt2.write(item);
//                        out[index - 2].write(item);
                    } else if (countdown <= 2) // Signal consumer to end
                    {
                        channelInputInt2.read();
                        channelOutputInt2.write(-1);
//                        req[index - 2].read(); // Read and discard request
//                        out[index - 2].write(-1); // Signal end
                        countdown--;
                    }
                    break;
            } // switch
        } // while
        System.out.println("Buffer ended.");
    } // run
} // class Buffer

//): (1) zaimplementuj rozwiazanie 1P1K narysowane na Rys.5.5 (str.157), (2)
// zaimplementuj rozwiazanie 1P1K narysowane na
// Rys.5.6 (z procesem posredniczacym - 1-elementowym buforem) (str.157)
// (3) jak pokonuje  sie
// (a) koniecznosc znania nazw procesów,
// (b) brak mozliwosci umieszczania instrukcji wyjscia w dozorze?
// (4) na czym polega pomysł na rozwiazanie ograniczonego bufora w CSP?

// rozproszyć bufor - wiele procesów - na każdy (kilka?) element bufora proces
// który producent, który konument do którego elementu bufora?
// jak komunikują się ze sobą elementy bufora - kto z kim musi się komunikować żeby usprawnić pracę producentów i konsumentów


// ocena zależy od sposobu komunikacji między elementami bufora
// podpowiedź - przekazywanie instrukcji sterujących, np do którego bufora mamy się udać
// oddzielić komunikację danych od sterowania komunikatami
// czyli wprowadzić punkt centralny dla komunikatow sterujacych
// zaprojektować powyższy bufor i zaimplementować jego peirwszą wersję
// pierwsze zajecia po świętach - na po 3 królach, ale zrobić wcześniej
// chyba przygotować się z sieci Petriego i csp

