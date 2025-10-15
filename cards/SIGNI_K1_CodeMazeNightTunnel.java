package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.MillCost;

public final class SIGNI_K1_CodeMazeNightTunnel extends Card {

    public SIGNI_K1_CodeMazeNightTunnel()
    {
        setImageSets("WX24-P4-084");

        setOriginalName("コードメイズ　ヨルトンネル");
        setAltNames("コードメイズヨルトンネル Koodo Meizu Yoru Tonneru");
        setDescription("jp",
                "@E：対戦相手の場にあるすべてのシグニを好きなように配置し直す。\n" +
                "@A $T1 @[デッキの上からカードを３枚トラッシュに置く]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。"
        );

        setName("en", "Code Maze Night Tunnel");
        setDescription("en",
                "@E: Rearrange all of your opponent's SIGNI on the field as you like.\n" +
                "@A $T1 @[Put   the top 3 cards of your deck into the trash]@: Until the end of your opponent's next turn, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "迷宫代号 夜隧道");
        setDescription("zh_simplified", 
                "@E :对战对手的场上的全部的精灵任意重新配置。\n" +
                "@A $T1 从牌组上面把3张牌放置到废弃区:直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new MillCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private void onEnterEff()
        {
            rearrangeAll(getOpponent());
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
