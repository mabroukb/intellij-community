// "Transform body to single exit-point form" "true"
class Test {
    String test(String[] strings) {
        String res = null;
        boolean finished = false;
        if (strings.length > 2) {
            String string = strings[0];
            if (string.equals(strings[1])) {
                finished = true;
                res = foo(string);
            }
        }
        if (!finished) {
            String result = bar();
            res = result;
        }
        return res;
    }
}