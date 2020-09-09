package scripting.NPC;

public class NPCTalk {

    private byte type, msg, param, color;
    private int npcid, npcidd, seconds, amount, min, max;
    private boolean prev, next;
    private String text, def, hint;
    private Object[] args;
    private boolean isZeroBeta = false;

    public NPCTalk(byte type, int npcid, byte msg) {
        this(type, npcid, msg, (byte) 0, (byte) 0);
    }

    public NPCTalk(byte type, int npcid, byte msg, byte param, byte color) {
        this.type = type;
        this.npcid = npcid;
        this.msg = msg;
        this.param = param;
        this.color = color;
    }

    /**
     * Get the type.
     *
     * @return
     */
    public byte getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Get the message modifier.
     *
     * @return
     */
    public byte getMsg() {
        return msg;
    }

    /**
     * Set the message modifier.
     *
     * @param msg
     */
    public void setMsg(byte msg) {
        this.msg = msg;
    }

    /**
     * Get the parameter.
     *
     * @return
     */
    public byte getParam() {
        return param;
    }

    /**
     * Set the parameter.
     *
     * @param param
     */
    public void setParam(byte param) {
        this.param = param;
    }

    /**
     * Get the color type.
     *
     * @return
     */
    public byte getColor() {
        return color;
    }

    /**
     * Set the color of the dialog box.
     *
     * @param color
     */
    public void setColor(byte color) {
        this.color = color;
    }

    /**
     * Get the NPC ID.
     *
     * @return
     */
    public int getNpcID() {
        return npcid;
    }

    /**
     * Set the NPC ID.
     *
     * @param npcid
     */
    public void setNpcID(int npcid) {
        this.npcid = npcid;
    }

    /**
     * Get the second NPC ID
     *
     * @return
     */
    public int getNpcIDD() {
        return npcidd;
    }

    /**
     * Set the second NPC ID
     *
     * @param npcidd
     */
    public void setNpcIDD(int npcidd) {
        this.npcidd = npcidd;
    }

    /**
     * Get the amount of seconds to wait.
     *
     * @return
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Set the amount of seconds to wait.
     *
     * @param wait
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Get the amount.
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Set the amount.
     *
     * @param wait
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Get the minimum amount of character/numerical values.
     *
     * @param min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum amount of character/numerical values.
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get the maximum amount of character/numerical values.
     *
     * @param max
     */
    public int getMax() {
        return max;
    }

    /**
     * Set the maximum amount of character/numerical values.
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get the text to be displayed in the dialog box.
     *
     * @param text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text to be displayed in the dialog box.
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Add text to the current text string.
     *
     * @param text
     */
    public void addText(String text) {
        this.text += text;
    }

    /**
     * Get the default text to be displayed in the dialog box.
     *
     * @param def
     */
    public String getDef() {
        return def;
    }

    /**
     * Set the default text to be displayed in the dialog box.
     *
     * @param def
     */
    public void setDef(String def) {
        this.def = def;
    }

    /**
     * Get the hint.
     *
     * @param hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Set the hint.
     *
     * @param hint
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Get the status of the previous button.
     *
     * @param prev
     */
    public boolean getPrev() {
        return prev;
    }

    /**
     * Toggle the status of the previous button.
     *
     * @param prev
     */
    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    /**
     * Get the status of the next button.
     *
     * @param next
     */
    public boolean getNext() {
        return next;
    }

    /**
     * Toggle the status of the next button.
     *
     * @param next
     */
    public void setNext(boolean next) {
        this.next = next;
    }

    /**
     * Get the list of objects.
     *
     * @param args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Set the list of objects.
     *
     * @param args
     */
    public void setArgs(Object... args) {
        this.args = args;
    }

    public void setZeroBeta(boolean a) {
        this.isZeroBeta = a;
    }

    public boolean isZeroBeta() {
        return isZeroBeta;
    }
}
