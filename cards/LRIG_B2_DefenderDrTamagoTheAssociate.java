package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_DefenderDrTamagoTheAssociate extends Card {

    public LRIG_B2_DefenderDrTamagoTheAssociate()
    {
        setImageSets("WXDi-P15-029");

        setOriginalName("アソシエイト　防衛者Ｄｒ．タマゴ");
        setAltNames("アソシエイトボウエイシャドクタータマゴ Asoshieita Boueisha Dokutaa Tamago");

        setName("en", "Associate! Defender Dr. Tamago");
        
        
        setName("en_fan", "Defender Dr. Tamago, the Associate");

		setName("zh_simplified", "准教授 防卫者Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
