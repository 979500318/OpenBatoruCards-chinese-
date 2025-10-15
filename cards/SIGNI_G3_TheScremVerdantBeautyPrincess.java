package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_TheScremVerdantBeautyPrincess extends Card {

    public SIGNI_G3_TheScremVerdantBeautyPrincess()
    {
        setImageSets("WX24-P4-048");

        setOriginalName("翠美姫　ムンクシャウト");
        setAltNames("スイビキムンクシャウト Suibiki Munku Shauto");
        setDescription("jp",
                "@C：あなたの他のシグニのパワーを＋2000する。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュからシグニ１枚を対象とし、それをデッキの一番下に置いてもよい。その後、この方法でデッキに移動したシグニと同じパワーの対戦相手のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、それをエナゾーンに置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "The Scream, Verdant Beauty Princess");
        setDescription("en",
                "@C: All of your other SIGNI get +2000 power.\n" +
                "@U: Whenever this SIGNI attacks, target 1 SIGNI from your trash, and you may put it on the bottom of your deck. Then, target 1 of your opponent's SIGNI with the same power as the SIGNI put into the deck this way, and you may pay %G %X. If you do, put it into the ener zone." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "翠美姬 呐喊");
        setDescription("zh_simplified", 
                "@C :你的其他的精灵的力量+2000。\n" +
                "@U :当这只精灵攻击时，从你的废弃区把精灵1张作为对象，可以将其放置到牌组最下面。与这个方法往牌组移动的精灵相同力量的对战对手的精灵1只作为对象，可以支付%G%X。这样做的场合，将其放置到能量区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().SIGNI().except(cardId), new PowerModifier(2000));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().fromTrash()).get();
            
            if(target != null && playerChoiceActivate())
            {
                double power = target.getIndexedInstance().getPower().getValue();
                
                if(returnToDeck(target, DeckPosition.BOTTOM))
                {
                    target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(power)).get();
                    
                    if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                    {
                        putInEner(target);
                    }
                }
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
