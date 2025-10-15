package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_MaddogDissonaVerdantWisdom extends Card {

    public SIGNI_G3_MaddogDissonaVerdantWisdom()
    {
        setImageSets("WXDi-P12-081");

        setOriginalName("翠英　マッドグ//ディソナ");
        setAltNames("スイエイマッドグディソナ Suiei Maddogu Disona");
        setDescription("jp",
                "@U：このシグ二がアタックしたとき、【エナチャージ１】をする。このシグニのパワーが15000の場合、代わりに【エナチャージ２】をする。"
        );

        setName("en", "Mad Dogma//Dissona, Jade Mind");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]. If this SIGNI's power is exactly 15000, instead [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Maddog//Dissona, Verdant Wisdom");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]. If this SIGNI's power is 15000, [[Ener Charge 2]] instead."
        );

		setName("zh_simplified", "翠英 疯狂怪医//失调");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，[[能量填充1]]。这只精灵的力量在15000的场合，作为替代，[[能量填充2]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            enerCharge(getPower().getValue() != 15000 ? 1 : 2);
        }
    }
}
