package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_SukunaBiKonaVerdantAngel extends Card {
    
    public SIGNI_G2_SukunaBiKonaVerdantAngel()
    {
        setImageSets("WXDi-P06-073");
        
        setOriginalName("翠天　スクナビコナ");
        setAltNames("スイテンスクナビコナ Suiten Sukuna Bi Kona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、それをエナゾーンに置く。\n" +
                "$$2あなたのエナゾーンから＜天使＞のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Sukunabikona, Jade Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, choose one of the following. \n" +
                "$$1 Put target <<Angel>> SIGNI from your trash into your Ener Zone.\n" +
                "$$2 Add target <<Angel>> SIGNI from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Sukuna Bi Kona, Verdant Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 <<Angel>> SIGNI from your trash, and put it into the ener zone.\n" +
                "$$2 Target 1 <<Angel>> SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "翠天 少名毘古那");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 从你的废弃区把<<天使>>精灵1张作为对象，将其放置到能量区。\n" +
                "$$2 从你的能量区把<<天使>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
                putInEner(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner()).get();
                addToHand(target);
            }
        }
    }
}
