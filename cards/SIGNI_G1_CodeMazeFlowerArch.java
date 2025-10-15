package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_CodeMazeFlowerArch extends Card {

    public SIGNI_G1_CodeMazeFlowerArch()
    {
        setImageSets("WX24-P2-083");

        setOriginalName("コードメイズ　フラワーアーチ");
        setAltNames("コードメイズフラワーアーチ Koodo Meizu Furawaa Aachi");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋4000される。\n" +
                "@E：対戦相手の場にあるすべてのシグニを好きなように配置し直す。"
        );

        setName("en", "Code Maze Flower Arch");
        setDescription("en",
                "@C: As long as this SIGNI is in the center SIGNI zone, this SIGNI gets +4000 power.\n" +
                "@E: Rearrange all of your opponent's SIGNI on the field as you like."
        );

		setName("zh_simplified", "迷宫代号 绢花拱门");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+4000。\n" +
                "@E :对战对手的场上的全部的精灵任意重新配置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            rearrangeAll(getOpponent());
        }
    }
}
