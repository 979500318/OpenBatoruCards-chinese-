package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_AudumblaAzureAngel extends Card {
    
    public SIGNI_B1_AudumblaAzureAngel()
    {
        setImageSets("WXDi-P03-064");
        
        setOriginalName("蒼天　アウドムラ");
        setAltNames("ソウテンアウドムラ Souten Audomura Audumbla Audomula");
        setDescription("jp",
                "@U $T2：ドローフェイズ以外であなたがカードを１枚引いたとき、ターン終了時まで、あなたのすべてのシグニのパワーを＋1000する。\n" +
                "@U：あなたのアタックフェイズ開始時、手札から＜天使＞のシグニを１枚捨ててもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Audomula, Azure Angel");
        setDescription("en",
                "@U $T2: Whenever you draw a card anytime other than draw phase, all SIGNI on your field get +1000 power until end of turn.\n" +
                "@U: At the beginning of your attack phase, you may discard an <<Angel>> SIGNI. If you do, draw a card."
        );
        
        setName("en_fan", "Auðumbla, Azure Angel");
        setDescription("en_fan",
                "@U $T2: Whenever you draw 1 card other than during your draw phase, until end of turn, all of your SIGNI get +1000 power.\n" +
                "@U: At the beginning of your attack phase, you may discard 1 <<Angel>> SIGNI from your hand. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "苍天 欧德姆布拉");
        setDescription("zh_simplified", 
                "@U $T2 :当在抽牌阶段以外你抽1张牌时，直到回合结束时为止，你的全部的精灵的力量+1000。\n" +
                "@U :你的攻击阶段开始时，可以从手牌把<<天使>>精灵1张舍弃。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.DRAW, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 2);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && getCurrentPhase() != GamePhase.DRAW ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOwner()), 1000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL)).get() != null)
            {
                draw(1);
            }
        }
    }
}
