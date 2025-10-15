package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_WilliamTellVerdantGeneral extends Card {
    
    public SIGNI_G1_WilliamTellVerdantGeneral()
    {
        setImageSets("WXDi-P05-070");
        
        setOriginalName("翠将　ウィリアムテル");
        setAltNames("スイショウウィリアムテル Suishou Uiriamu Teru");
        setDescription("jp",
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、[[エナチャージ１]]をする。"
        );
        
        setName("en", "William Tell, Jade General");
        setDescription("en",
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "William Tell, Verdant General");
        setDescription("en_fan",
                "@U: Whenever this SIGNI banishes 1 of your opponent's SIGNI in battle, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "翠将 威廉退尔");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，[[能量填充1]]。\n"
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
