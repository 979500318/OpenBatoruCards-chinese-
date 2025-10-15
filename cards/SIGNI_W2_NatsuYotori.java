package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class SIGNI_W2_NatsuYotori extends Card {

    public SIGNI_W2_NatsuYotori()
    {
        setImageSets("WX25-CP1-057");

        setOriginalName("柚鳥ナツ");
        setAltNames("ユトリナツ Yotori Natsu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他の＜ブルアカ＞のシグニ１体を対象とし、ターン終了時まで、それは【シュート】を得る。" +
                "~{{E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Yotori Natsu");

        setName("en_fan", "Natsu Yotori");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your other <<Blue Archive>> SIGNI, and until end of turn, it gains [[Shoot]]." +
                "~{{E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "柚鸟夏");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的<<ブルアカ>>精灵1只作为对象，直到回合结束时为止，其得到[[击落]]。\n" +
                "~{{E从手牌把<<ブルアカ>>牌2张舍弃从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            EnterAbility enter = registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            if(target != null) attachAbility(target, new StockAbilityShoot(), ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().guard().fromTrash()).get();
            addToHand(target);
        }
    }
}
