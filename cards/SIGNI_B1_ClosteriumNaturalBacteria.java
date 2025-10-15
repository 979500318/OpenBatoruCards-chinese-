package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_ClosteriumNaturalBacteria extends Card {

    public SIGNI_B1_ClosteriumNaturalBacteria()
    {
        setImageSets("WX24-P4-105");

        setOriginalName("羅菌 ミカヅキモ");
        setAltNames("ラキンミカヅキモ Rakin Mikazukimo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手の手札を１枚見ないで選び、公開させる。あなたの場にそのカードと共通する色を持つルリグがいる場合、そのカードを捨てさせる。"
        );

        setName("en", "Closterium, Natural Bacteria");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, choose 1 card from your opponent's hand without looking, and reveal it. If 1 of your LRIG on the field shares a common color with that card, discard it."
        );

		setName("zh_simplified", "罗菌 新月藻");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，不看对战对手的手牌选1张，公开。你的场上的持有与那张牌共通颜色的分身的场合，那张牌舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(1);
        setPower(2000);

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
        }
        
        private void onAutoEff()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            
            if(reveal(cardIndex))
            {
                if(new TargetFilter().own().anyLRIG().withColor(cardIndex.getIndexedInstance().getColor()).getValidTargetsCount() > 0)
                {
                    discard(cardIndex);
                } else {
                    addToHand(cardIndex);
                }
            }
        }
    }
}
