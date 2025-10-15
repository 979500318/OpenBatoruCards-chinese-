package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_CodeMazeKyoushuu extends Card {

    public SIGNI_W1_CodeMazeKyoushuu()
    {
        setImageSets("WXDi-P09-049");

        setOriginalName("コードメイズ　キョウシュウ");
        setAltNames("コードメイズキョウシュウ Koodo Meizu Kyoushuu");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは[[シャドウ（レベル１）]]を得る。"
        );

        setName("en", "Driver's Ed, Code: Maze");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow -- Level one]]. "
        );
        
        setName("en_fan", "Code Maze Kyoushuu");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI gains [[Shadow (level 1)]]."
        );

		setName("zh_simplified", "迷宫代号 驾校");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵得到[[暗影（等级1）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getLevel().getValue() == 1 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
