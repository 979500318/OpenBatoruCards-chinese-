package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_OkatotokiNaturalPlant extends Card {
    
    public SIGNI_B2_OkatotokiNaturalPlant()
    {
        setImageSets("WXDi-P06-067");
        
        setOriginalName("羅植　オカトトキ");
        setAltNames("ラショクオカトトキ Rashoku Okatotoki");
        setDescription("jp",
                "@C：あなたのエナゾーンにカードが６枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンにあるカードが５枚以下の場合、各プレイヤーは【エナチャージ１】をする。"
        );
        
        setName("en", "Okatotoki, Natural Plant");
        setDescription("en",
                "@C: As long as there are six or more cards in your Ener Zone, this SIGNI gets +5000 power.\n" +
                "@U: At the beginning of your attack phase, if there are five or less cards in your Ener Zone, each player [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Okatotoki, Natural Plant");
        setDescription("en_fan",
                "@C: As long as there are 6 or more cards in your ener zone, this SIGNI gets +5000 power.\n" +
                "@U: At the beginning of your attack phase, if there are 5 or less cards in your ener zone, each player does [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "罗植姬 桔梗");
        setDescription("zh_simplified", 
                "@C :你的能量区的牌在6张以上时，这只精灵的力量+5000。\n" +
                "@U :你的攻击阶段开始时，你的能量区的牌在5张以下的场合，各玩家[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getEnerCount(getOwner()) >= 6 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getEnerCount(getOwner()) <= 5)
            {
                enerCharge(1);
                enerCharge(getOpponent(), 1);
            }
        }
    }
}
