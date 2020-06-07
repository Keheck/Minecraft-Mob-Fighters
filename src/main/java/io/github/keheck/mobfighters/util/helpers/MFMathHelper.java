package io.github.keheck.mobfighters.util.helpers;

public class MFMathHelper
{
    private MFMathHelper() {}

    public static float cycleFloat(float num, float min, float max, float step)
    {
        num += step;

        if(step < 0)
        {
            if(num < min)
            {
                float diff = min - num;
                num = max - diff;
            }

        }
        else
        {
            if(num > max)
            {
                float diff = num - max;
                num = min + diff;
            }
        }

        return num;
    }
}
