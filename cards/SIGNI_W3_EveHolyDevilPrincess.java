package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_W3_EveHolyDevilPrincess extends Card {

    public SIGNI_W3_EveHolyDevilPrincess()
    {
        setImageSets("WX24-P2-048");
        setLinkedImageSets("WX24-P2-014");

        setOriginalName("聖魔姫　イヴ");
        setAltNames("セイマキイヴ Seimaki Ivu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《満月の使徒　小湊るう子》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それのレベル１につき手札から白のカードを１枚捨ててもよい。そうした場合、それを手札に戻す。\n" +
                "$$2手札をすべて捨ててもよい。この方法でカードを６枚以上捨てた場合、対戦相手のライフクロス１枚を手札に加えさせる。\n" +
                "@A #D：あなたの場に＜天使＞のシグニがある場合、カードを１枚引く。"
        );

        setName("en", "Eve, Holy Devil Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Ruuko Kominato, Full Moon Apostle\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and you may discard 1 white card from your hand for each of its levels. If you do, return it to their hand.\n" +
                "$$2 You may discard all cards from your hand. If you discarded 6 or more cards this way, your opponent adds 1 of their life cloth to hand.\n" +
                "@A #D: If there is an <<Angel>> SIGNI on your field, draw 1 card."
        );

		setName("zh_simplified", "圣魔姬 夏娃");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《満月の使徒　小湊るう子》的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就从手牌把白色的牌1张舍弃。这样做的场合，将其返回手牌。\n" +
                "$$2 可以把手牌全部舍弃。这个方法把牌6张以上舍弃的场合，对战对手的生命护甲1张加入手牌。\n" +
                "@A #D:你的场上有<<天使>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

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

            registerActionAbility(new DownCost(), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("満月の使徒　小湊るう子"))
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                    if(target != null)
                    {
                        int level = target.getIndexedInstance().getLevel().getValue();
                        DataTable<CardIndex> data = discard(0,level, ChoiceLogic.BOOLEAN, new TargetFilter().withColor(CardColor.WHITE));
                        if((data.get() != null && data.size() == level) || (data.get() == null && level == 0))
                        {
                            addToHand(target);
                        }
                    }
                } else {
                    if(playerChoiceActivate())
                    {
                        int countDiscarded = discard(getCardsInHand(getOwner())).size();
                        if(countDiscarded >= 6)
                        {
                            addToHand(getOpponent(), CardLocation.LIFE_CLOTH);
                        }
                    }
                }
            }
        }

        private void onActionEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
    }
}
