package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_WutuguVerdantGeneral extends Card {
    
    public SIGNI_G1_WutuguVerdantGeneral()
    {
        setImageSets("WXDi-P03-073");
        
        setOriginalName("翠将　ゴツトツコツ");
        setAltNames("スイショウゴツトツコツ Suishou Gotsutotsukotsu");
        setDescription("jp",
                "@U：このシグニが場からエナゾーンに置かれたとき、あなたのエナゾーンにあるカードが２枚以下の場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Wutugu, Jade General");
        setDescription("en",
                "@U: When this SIGNI is put into the Ener Zone from the field, if you have two or less cards in your Ener Zone, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Wutugu, Verdant General");
        setDescription("en_fan",
                "@U: When this SIGNI is put from the field into the ener zone, if there are 2 or less cards in your ener zone, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "翠将 兀突骨");
        setDescription("zh_simplified", 
                "@U :当这只精灵从场上放置到能量区时，你的能量区的牌在2张以下的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getCardIndex().isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getEnerCount(getOwner()) <= 2)
            {
                enerCharge(1);
            }
        }
    }
}
