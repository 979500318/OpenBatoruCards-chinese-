package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B4_CaptainHookClawOfDemonicSeas extends Card {

    public SIGNI_B4_CaptainHookClawOfDemonicSeas()
    {
        setImageSets("WXK01-039");

        setOriginalName("魔海の鉤爪　キャプテン・フック");
        setAltNames("マカイノカギヅメキャプテンフック Makai no Kagidzume Kyaputen Fukku");
        setDescription("jp",
                "@C：あなたの手札が０枚であるかぎり、このシグニは対戦相手の効果によってバニッシュされない。\n" +
                "@E @[手札を２枚捨てる]@：あなたのデッキの上からカードを５枚公開する。その中から＜悪魔＞のシグニを２枚まで場に出し、残りをトラッシュに置く。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンし凍結する。"
        );

        setName("en", "Captain Hook, Claw of Demonic Seas");
        setDescription("en",
                "@C: As long as there are 0 cards in your hand, this SIGNI can't be banished by your opponent's effects.\n" +
                "@E @[Discard 2 cards from your hand]@: Reveal the top 5 cards of your deck. Put up to 2 <<Devil>> SIGNI from among them onto the field, and put the rest into the trash." +
                "~#Target up to 2 of your opponent's SIGNI, and down and freeze them."
        );

		setName("zh_simplified", "魔海的钩爪 船长·霍克");
        setDescription("zh_simplified", 
                "@C :你的手牌在0张时，这只精灵不会因为对战对手的效果破坏。\n" +
                "@E 手牌2张舍弃:从你的牌组上面把5张牌公开。从中把<<悪魔>>精灵2张最多出场，剩下的放置到废弃区。" +
                "~#对战对手的精灵2只最多作为对象，将这些#D并冻结。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEffModRuleCheck));
            
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) == 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onEnterEff()
        {
            reveal(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromRevealed().playable());
            putOnField(data);
            
            trash(getCardsInRevealed(getOwner()));
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
            down(data);
            freeze(data);
        }
    }
}
