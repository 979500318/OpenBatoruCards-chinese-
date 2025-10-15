package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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

public final class SIGNI_WK3_YogNigguraFusedGateAngelPrincess extends Card {

    public SIGNI_WK3_YogNigguraFusedGateAngelPrincess()
    {
        setImageSets("WXDi-P16-055");
        setLinkedImageSets("WXDi-P16-013","WXDi-P16-008","WXDi-P16-001B");

        setOriginalName("極門天姫　ヨグニグラ");
        setAltNames("ゴクモンテンキヨグニグラ Gokumon Tenki Yogu Nigura");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にカード名に《扉の俯瞰者》を含むルリグがいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから#Gを持つシグニ１枚を対象とし、%Wを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "$$2対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "@A $T1 %K0：あなたのデッキの上からカードを３枚トラッシュに置く。"
        );

        setName("en", "Yog - Niggura, Ultra Gate Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a LRIG with a card name that includes \"Gate Overseer\" on your field, choose one of the following.\n$$1You may pay %W. If you do, add target SIGNI with a #G from your trash to your hand.\n$$2You may pay %K. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n@A $T1 %K0: Put the top three cards of your deck into your trash."
        );
        
        setName("en_fan", "Yog-Niggura, Fused Gate Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if your LRIG contains \"Door Overseer\" in its name, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 #G @[Guard]@ SIGNI from your trash, and you may pay %W. If you do, add it to your hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --10000 power.\n" +
                "@A $T1 %K0: Put the top 3 cards of your deck into the trash."
        );

		setName("zh_simplified", "极门天姬 犹格尼古拉");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有牌名含有《扉の俯瞰者》的分身的场合，从以下的2种选1种。\n" +
                "$$1 从你的废弃区把持有#G的精灵1张作为对象，可以支付%W。这样做的场合，将其加入手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@A $T1 %K0:从你的牌组上面把3张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL,CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getCardReference().getOriginalName().contains("扉の俯瞰者"))
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
                    
                    if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
                    {
                        addToHand(target);
                    }
                } else {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    
                    if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                    {
                        gainPower(target, -10000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
        
        private void onActionEff()
        {
            millDeck(3);
        }
    }
}
