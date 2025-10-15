package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableInteger;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.ModifiableBaseValueModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_SaturnNaturalStarPrincess extends Card {
    
    public SIGNI_B3_SaturnNaturalStarPrincess()
    {
        setImageSets("WXDi-P01-039");
        
        setOriginalName("羅星姫　サタン");
        setAltNames("ラセイキサタン Raseiki Satan");
        setDescription("jp",
                "@C：あなたのデッキとトラッシュにあるレベル３とレベル２のシグニの基本レベルは１になる。\n" +
                "@C：あなたのターンの間、対戦相手の効果によってあなたのレベル１のシグニはバニッシュされない。" +
                "~#：@[@|どちらか１つを選ぶ。|@]@\n" +
                "$$1 対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2 カードを１枚引く。"
        );
        
        setName("en", "Saturne, Natural Planet Queen");
        setDescription("en",
                "@C: The base level of all level two and level three SIGNI in your deck and trash become one.\n" +
                "@C: During your turn, level one SIGNI on your field cannot be vanished by your opponent's effects." +
                "~#Choose one --\n" +
                "$$1 Down up to two target SIGNI on your opponent's field.\n" +
                "$$2 Draw a card."
        );
        
        setName("en_fan", "Saturn, Natural Star Princess");
        setDescription("en_fan",
                "@C: The base level of all level 3 and level 2 SIGNI in your deck and trash becomes 1.\n" +
                "@C: During your turn, your level 1 SIGNI can't be banished by your opponent's effects." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "罗星姬 土星");
        setDescription("zh_simplified", 
                "@C :你的牌组和废弃区的等级3和等级2的精灵的基本等级变为1。\n" +
                "@C :你的回合期间，不会因为对战对手的效果把你的等级1的精灵破坏。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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
            
            registerConstantAbility(new TargetFilter().own().SIGNI().withLevel(1,3).fromLocation(CardLocation.DECK_MAIN,CardLocation.TRASH),
                new ModifiableBaseValueModifier<>(this::onConstEffShared1ModGetSample, () -> 1)
            );
            
            registerConstantAbility(this::onConstEff2SharedCond,
                new TargetFilter().own().SIGNI().withLevel(1),
                new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEff2SharedModRuleCheck)
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ModifiableInteger onConstEffShared1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLevel();
        }
        
        private ConditionState onConstEff2SharedCond(CardIndex cardIndex)
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEff2SharedModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
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
