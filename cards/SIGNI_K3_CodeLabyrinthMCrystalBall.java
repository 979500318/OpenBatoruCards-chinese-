package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K3_CodeLabyrinthMCrystalBall extends Card {

    public SIGNI_K3_CodeLabyrinthMCrystalBall()
    {
        setImageSets("SPDi43-20");
        setLinkedImageSets("SPDi43-14");

        setOriginalName("コードラビリンス　Mミラーボ");
        setAltNames("コードラビリンスエムミラーボ Koodo Rabirinsu Emu Miraabo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に《VOGUE3-EXTREME ムジカ》がいる場合、あなたの他のレベル２以上のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、それとこのシグニの場所を入れ替える。\n" +
                "@E：対戦相手の場にあるすべてのシグニを好きなように配置し直す。"
        );

        setName("en", "Code Labyrinth M Crystal Ball");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Muzica, VOGUE 3-EXTREME\", target 1 of your other level 2 or higher SIGNI, and you may pay %K %X. If you do, exchange its position with this SIGNI.\n" +
                "@E: Rearrange all of your opponent's SIGNI on the field as you like."
        );

		setName("zh_simplified", "迷牢代号 M镜爆");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有《VOGUE3-EXTREME:ムジカ》的场合，你的其他的等级2以上的精灵1只作为对象，可以支付%K%X。这样做的场合，将其与这只精灵的场所交换。\n" +
                "@E :对战对手的场上的全部的精灵任意重新配置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("VOGUE3-EXTREME ムジカ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI().withLevel(2,0).except(getCardIndex())).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
                {
                    exchange(target, getCardIndex());
                }
            }
        }

        private void onEnterEff()
        {
            rearrangeAll(getOpponent());
        }
    }
}
