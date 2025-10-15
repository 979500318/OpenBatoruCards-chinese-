package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_DefenderDrTamago extends Card {

    public LRIG_B0_DefenderDrTamago()
    {
        setImageSets("WXDi-P15-027");

        setOriginalName("防衛者Ｄｒ．タマゴ");
        setAltNames("ボウエイシャドクタータマゴ Boueisha Dokutaa Tamago");

        setName("en", "Defender Dr. Tamago");
        
        
        setName("en_fan", "Defender Dr. Tamago");

		setName("zh_simplified", "防卫者Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
