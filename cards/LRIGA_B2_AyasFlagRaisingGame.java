package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_AyasFlagRaisingGame extends Card {

    public LRIGA_B2_AyasFlagRaisingGame()
    {
        setImageSets("WX25-P2-051");

        setOriginalName("あーやの旗あげゲーム！");
        setAltNames("アーヤノハタアゲゲーム Aaya no Hata age Geemu");
        setDescription("jp",
                "@E：カードを３枚引く。\n" +
                "@E：あなたの手札からカードを１枚まで裏向きでルリグゾーンに置く。次の対戦相手のアタックフェイズ開始時、そのカードを表向きにしてトラッシュに置き、そのカードと同じレベルの対戦相手のすべてのシグニをダウンする。"
        );

        setName("en", "Aya's Flag-Raising Game!");
        setDescription("en",
                "@E: Draw 3 cards.\n" +
                "@E: Put up to 1 card from your hand onto the LRIG zone face down. At the beginning of your opponent's next attack phase, put that card into the trash, and down all of your opponent's SIGNI with the same level as that card."
        );

		setName("zh_simplified", "亚弥的举旗游戏！");
        setDescription("zh_simplified", 
                "@E :抽3张牌。\n" +
                "@E 从你的手牌把牌1张最多里向放置到分身区。下一个对战对手的攻击阶段开始时，那张牌表向放置到废弃区，与那张牌相同等级的对战对手的全部的精灵#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            draw(3);
        }
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ZONE).own().fromHand()).get();
            
            if(putOnZone(cardIndex, CardLocation.LRIG, CardUnderType.ZONE_GENERIC))
            {
                callDelayedEffect(ChronoDuration.nextPhase(getOpponent(), GamePhase.ATTACK_PRE), () -> {
                    if(trash(cardIndex) && CardType.isSIGNI(cardIndex.getCardReference().getType()))
                    {
                        down(new TargetFilter().OP().SIGNI().withLevel(cardIndex.getIndexedInstance().getLevel().getValue()).getExportedData());
                    }
                });
            }
        }
    }
}
