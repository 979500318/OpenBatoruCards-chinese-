package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_W4_RuukoKominatoPurposeOfDaysPassing extends Card {

    public LRIG_W4_RuukoKominatoPurposeOfDaysPassing()
    {
        setImageSets("WX24-P4-012", "WX24-P4-012U");

        setOriginalName("進日の使途　小湊るう子");
        setAltNames("シンジツノシトコミナトルウコ Shinjitsu no Shito Kominato Ruuko");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜るう子＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのデッキの上からカードを１０枚見る。その中からカードを２枚まで手札に加え、残りをデッキに加えてシャッフルする。\n" +
                "@A $G1 @[@|夢限の理|@]@ %W0：&E４枚以上@0対戦相手のシグニ１体を対象とし、それをトラッシュに置く。このターンの、次のあなたのアタックフェイズ開始時、あなたのすべてのシグニをアップする。"
        );

        setName("en", "Ruuko Kominato, Purpose of Days Passing");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Ruuko>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Look at the top 10 cards of your deck. Add up to 2 cards from among them to your hand, and shuffle the rest into your deck.\n" +
                "@A $G1 @[@|Eternal Principle|@]@ %W0: &E4 or more@0 Target 1 of your opponent's SIGNI, and put it into the trash. At the beginning of your next attack phase this turn, up all of your SIGNI."
        );

		setName("zh_simplified", "进日的使途 小湊露子");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<るう子>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你的牌组上面看10张牌。从中把牌2张最多加入手牌，剩下的加入牌组洗切。\n" +
                "@A $G1 :梦限之理%W0&E4张以上@0对战对手的精灵1只作为对象，将其放置到废弃区。这个回合的，下一个你的攻击阶段开始时，你的全部的精灵竖直。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.RUUKO);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.RUUKO).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Eternal Principle");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            look(10);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter().own().fromLooked());
            addToHand(data);
            
            returnToDeck(getCardsInLooked(getOwner()), DeckPosition.TOP);
            shuffleDeck();
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                trash(target);
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up(getSIGNIOnField(getOwner()));
        }
    }
}
