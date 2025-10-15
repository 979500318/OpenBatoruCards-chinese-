package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_SadestalDissonaNaturalStarPrincess extends Card {

    public SIGNI_W3_SadestalDissonaNaturalStarPrincess()
    {
        setImageSets("WXDi-P13-046");
        setLinkedImageSets("WXDi-P13-006");

        setOriginalName("羅星姫　サデスタル//ディソナ");
        setAltNames("ラセイキサデスタルディソナ Raseiki Sadesutaru Disona");
        setDescription("jp",
                "@U $T1：あなたのルリグ１体がアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから#Gを持つシグニ１枚を対象とし、%W %Xを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "$$2このターン、対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。\n" +
                "@U：あなたのターン終了時、あなたの場に《狂奏の巫女　リメンバ・テンペスト》がいる場合、カードを１枚引く。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Sadistal//Dissona, Planet Queen");
        setDescription("en",
                "@U $T1: When a LRIG on your field attacks, choose one of the following.\n$$1You may pay %W %X. If you do, add target SIGNI with a #G from your trash to your hand.\n$$2Your opponent cannot [[Guard]] unless they pay an additional %X this turn.\n@U: At the end of your turn, if \"Remember Tempest, Miko of Fantasia\" is on your field, draw a card." +
                "~#Return target SIGNI with power 10000 or less on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Sadestal//Dissona, Natural Star Princess");
        setDescription("en_fan",
                "@U $T1: When your LRIG attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 #G @[Guard]@ SIGNI from your trash, and you may pay %W %X. If you do, add it to your hand.\n" +
                "$$2 This turn, your opponent can't [[Guard]], unless they pay %X.\n" +
                "@U: At the end of your turn, if your LRIG is \"Remember Tempest, Lunatic Performing Miko\", draw 1 card." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "罗星姬 凌虐 //失调");
        setDescription("zh_simplified", 
                "@U $T1 :当你的分身1只攻击时，从以下的2种选1种。\n" +
                "$$1 从你的废弃区把持有#G的精灵1张作为对象，可以支付%W%X。这样做的场合，将其加入手牌。\n" +
                "$$2 这个回合，对战对手如果不追加把%X:支付，那么不能[[防御]]。\n" +
                "@U :你的回合结束时，你的场上有《狂奏の巫女　リメンバ・テンペスト》的场合，抽1张牌。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();

                if(target != null && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)))
                {
                    addToHand(target);
                }
            } else {
                addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_GUARD, getOpponent(), ChronoDuration.turnEnd(), data -> new EnerCost(Cost.colorless(1)));
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("狂奏の巫女　リメンバ・テンペスト"))
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}

