package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R4_CodeAccelF1 extends Card {

    public SIGNI_R4_CodeAccelF1()
    {
        setImageSets("WXK01-036");

        setOriginalName("コードアクセル　エフワン");
        setAltNames("コードアクセルエフワン Koodo Akuseru Efuwan");
        setDescription("jp",
                "\\C：【アサシン】\n" +
                "@C：あなたのターンの間、あなたの赤のシグニのパワーを＋2000する。\n" +
                "@E %R %R：あなたのデッキから《コードアクセル　エフワン》以外の＜乗機＞のシグニ１枚を探して場に出し、デッキをシャッフルする。" +
                "~#：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Accel F1");
        setDescription("en",
                "\\C: [[Assassin]]\n" +
                "@C: During your turn, all of your red SIGNI get +2000 power.\n" +
                "@E %R %R: Search your deck for 1 <<Riding Machine>> SIGNI other than \"Code Accel F1\", put it onto the field, and shuffle your deck." +
                "~#Target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "加速代号 一级方程式赛车");
        setDescription("zh_simplified", 
                "[驾驶]@C :[[暗杀]]\n" +
                "@C :你的回合期间，你的红色的精灵的力量+2000。\n" +
                "@E %R %R:从你的牌组找《コードアクセル　エフワン》以外的<<乗機>>精灵1张出场，牌组洗切。" +
                "~#对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
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

            Ability cont = registerStockAbility(new StockAbilityAssassin());
            cont.setCondition(this::onStockEffCond);
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withColor(CardColor.RED), new PowerModifier(2000));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onStockEffCond()
        {
            return isState(CardStateFlag.IN_DRIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = searchDeck(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE).except("コードアクセル　エフワン").playable()).get();
            putOnField(cardIndex);
            
            shuffleDeck();
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
