package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_HareHarePhantomBeast extends Card {
    
    public SIGNI_G3_HareHarePhantomBeast()
    {
        setImageSets("WXDi-D04-017");
        
        setOriginalName("幻獣　ウサウサ");
        setAltNames("ゲンジュウウサウサ Genjuu Usausa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2[[エナチャージ１]]" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ１]]"
        );
        
        setName("en", "Flopsy, Phantom Terra Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, choose one of the following.\n" +
                "$$1 Add target SIGNI from your Ener Zone to your hand.\n" +
                "$$2 [[Ener Charge 1]]." +
                "~#Choose one --\n" +
                "$$1 Vanish target upped SIGNI on your opponent's field.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
        setName("en_fan", "Hare Hare, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "$$2 [[Ener Charge 1]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "幻兽 兔兔");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(10000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            } else {
                enerCharge(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
