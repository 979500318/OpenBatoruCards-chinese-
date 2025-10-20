package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_B3_ArisTendouMaid extends Card {

    public SIGNI_B3_ArisTendouMaid()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-041");

        setOriginalName("天童アリス(メイド)");
        setAltNames("テンドウアリスメイド Tendou Arisu Meido");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から＜ブルアカ＞のカードを２枚捨て%Bを支払ってもよい。そうした場合、それをデッキの一番下に置く。\n" +
                "~{{U：あなたのアタックフェイズ開始時、あなたの場に＜ブルアカ＞のシグニが３種類以上ある場合、カードを１枚引く。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Tendou Aris (Maid)");

        setName("en_fan", "Aris Tendou (Maid)");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 2 <<Blue Archive>> cards from your hand and pay %B. If you do, put it on the bottom of their deck.\n" +
                "~{{U: At the beginning of your attack phase, if there are 3 or more different <<Blue Archive>> SIGNI on your field, draw 1 card.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "天童爱丽丝(女仆)");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把<<ブルアカ>>牌2张舍弃并支付%B。这样做的场合，将其放置到牌组最下面。\n" +
                "~{{U:你的攻击阶段开始时，你的场上的<<ブルアカ>>精灵在3种类以上的场合，抽1张牌。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null && payAll(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), new EnerCost(Cost.color(CardColor.BLUE, 1))))
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData().stream().
                map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
