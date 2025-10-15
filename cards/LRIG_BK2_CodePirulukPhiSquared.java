package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIG_BK2_CodePirulukPhiSquared extends Card {

    public LRIG_BK2_CodePirulukPhiSquared()
    {
        setImageSets("WX25-P2-033");

        setOriginalName("コード・ピルルク・φ²");
        setAltNames("コードピルルクファイスクエアド Koodo Piruruku Fai Sukueado Squared Φ φ");
        setDescription("jp",
                "=G 青かつ黒のルリグ\n" +
                "@C：このルリグは青かつ黒のルリグにしかグロウできない。"
        );

        setName("en", "Code Piruluk φ²");
        setDescription("en",
                "=G Blue and black LRIG\n" +
                "@C: This LRIG can only grow into LRIGs that are blue and black."
        );

		setName("zh_simplified", "代号·皮璐璐可·φ²");
        setDescription("zh_simplified", 
                "[[成长]]蓝色且黑色的分身（这张牌只能从持有蓝色和黑色的2种颜色的分身成长）\n" +
                "@C :这只分身只能成长为蓝色且黑色的分身。（只能是持有蓝色和黑色的2种颜色的分身在上面重叠）\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setCost(Cost.color(CardColor.BLUE, 1), Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.GROW, new TargetFilter().LRIG().withColor(CardColor.BLUE).withColor(CardColor.BLACK));
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_GROW, this::onConstEffModRuleCheck));
        }

        private RuleCheckState onConstEffModRuleCheck(CardRuleCheckData data)
        {
            return data.getCardIndex().getIndexedInstance().getColor().matches(CardColor.BLUE) &&
                   data.getCardIndex().getIndexedInstance().getColor().matches(CardColor.BLACK) ? RuleCheckState.IGNORE : RuleCheckState.BLOCK;
        }
    }
}

