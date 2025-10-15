package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_MarchosiasWickedDevil extends Card {

    public SIGNI_K1_MarchosiasWickedDevil()
    {
        setImageSets("WX24-P4-083");

        setOriginalName("凶魔　マルコシアス");
        setAltNames("キョウママルコシアス Kyouma Maruchoshiasu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたのシグニ１体を場からトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－3000する。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Marchosias, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put 1 of your SIGNI from the field into the trash. If you do, until end of turn, it gets --3000 power." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶魔 马加锡亚");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以把你的精灵1只从场上放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-3000。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
                
                if(trash(cardIndex))
                {
                    gainPower(target, -3000, ChronoDuration.turnEnd());
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
