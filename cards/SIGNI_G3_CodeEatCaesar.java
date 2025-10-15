package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_CodeEatCaesar extends Card {
    
    public SIGNI_G3_CodeEatCaesar()
    {
        setImageSets("WXDi-P04-078");
        
        setOriginalName("コードイート　シーザー");
        setAltNames("コードイートシーザー Koodo Iito Shiiza");
        setDescription("jp",
                "@U $T2：対戦相手のシグニ１体が場に出たとき、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Caesar, Code: Eat");
        setDescription("en",
                "@U $T2: Whenever a SIGNI enters your opponent's field, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Code Eat Caesar");
        setDescription("en_fan",
                "@U $T2: Whenever 1 of your opponent's SIGNI enters the field, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "食用代号 凯撒沙拉");
        setDescription("zh_simplified", 
                "@U $T2 :当对战对手的精灵1只出场时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
