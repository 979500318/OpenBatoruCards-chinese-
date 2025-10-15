package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_WhenIWasReincarnatedAsALevelOneMamaHeroITriedToBeUnparalleled extends Card {

    public SIGNI_R3_WhenIWasReincarnatedAsALevelOneMamaHeroITriedToBeUnparalleled()
    {
        setImageSets("WX24-P3-TK1A");

        setOriginalName("転生したらレベル１のママ勇者だったけど無双してみた");
        setAltNames("テンセイシタラレベルイチノママユウシャダッタケドムソウシテミタ Tensei shita ra Reberu Ichi no Mama Yuusha Datta kedo Musou shite Mita");
        setDescription("jp",
                "@C：このシグニのパワーはこのシグニのレベル１につき＋5000される。\n" +
                "@U：このシグニが対戦相手のシグニ１体をバニッシュしたとき、このシグニがレベル２以下の場合、このゲームの間、このシグニのレベルを＋１する。\n" +
                "@U：対戦相手のターン終了時、このシグニがレベル３以上の場合、対戦相手のシグニ１体を対象とし、それとこのシグニをゲームから除外する。\n" +
                "@A $T2 %R：このシグニのパワー以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "When I was Reincarnated as a Level 1 Mama Hero, I Tried to be Unparalleled");
        setDescription("en",
                "@C: This SIGNI gets +5000 power for each of its levels.\n" +
                "@U: Whenever this SIGNI banishes an opponent's SIGNI, if this SIGNI is level 2 or lower, for the duration of this game, this SIGNI gets +1 level.\n" +
                "@U: At the end of your opponent's turn, if this SIGNI is level 3 or higher, target 1 of your opponent's SIGNI, and exclude it and this SIGNI from the game.\n" +
                "@A $T2 %R: Target 1 of your opponent's SIGNI with power equal to or less than this SIGNI's, and banish it."
        );

		setName("zh_simplified", "转生等级1的妈妈勇者想要开无双");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据这只精灵的等级的数量，每有1级就+5000。\n" +
                "@U :当这只精灵把对战对手的精灵1只破坏时，这只精灵在等级2以下的场合，这场游戏期间，这只精灵的等级+1。\n" +
                "@U :对战对手的回合结束时，这只精灵在等级3以上的场合，对战对手的精灵1只作为对象，将其和这只精灵从游戏除外。\n" +
                "@A $T2 %R:这只精灵的力量以下的对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 2);
        }

        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 5000 * getLevel().getValue();
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLevel().getValue() <= 2)
            {
                gainValue(getCardIndex(), getLevel(), 1, ChronoDuration.permanent());
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLevel().getValue() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.EXCLUDE).OP().SIGNI()).get();
                
                exclude(target);
                exclude(getCardIndex());
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getPower().getValue())).get();
            banish(target);
        }
    }
}
