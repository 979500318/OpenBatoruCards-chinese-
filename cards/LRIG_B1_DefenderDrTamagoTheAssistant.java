package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_DefenderDrTamagoTheAssistant extends Card {

    public LRIG_B1_DefenderDrTamagoTheAssistant()
    {
        setImageSets("WXDi-P15-028");

        setOriginalName("アシスタント　防衛者Ｄｒ．タマゴ");
        setAltNames("アシスタントボウエイシャドクタータマゴ Ashisutanto Boueisha Dokutaa Tamago");

        setName("en", "Assistant! Defender Dr. Tamago");
        
        
        setName("en_fan", "Defender Dr. Tamago, the Assistant");

		setName("zh_simplified", "助教 防卫者Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
