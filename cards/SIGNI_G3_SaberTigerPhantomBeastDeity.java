package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_G3_SaberTigerPhantomBeastDeity extends Card {

    public SIGNI_G3_SaberTigerPhantomBeastDeity()
    {
        setImageSets("WXDi-P03-044");

        setOriginalName("幻獣神　サーベルタイガー");
        setAltNames("ゲンジュウシンサーベルタイガー Genjuushin Saaberu Taigaa");
        setDescription("jp",
                "=H 赤のルリグ１体\n\n" +
                "@C：あなたの他のシグニのパワーを＋3000する。\n" +
                "@U：このシグニがアタックしたとき、[[エナチャージ１]]をする。その後、このターンにあなたのデッキからカードが３枚以上エナゾーンに移動していた場合、%G %R %Xを支払ってもよい。そうした場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Saber Tiger, Phantom Beast Deity");
        setDescription("en",
                "=H One red LRIG \n" +
                "@C: Other SIGNI on your field get +3000 power.\n" +
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]. Then, if three or more cards were moved from your deck into your Ener Zone this turn, you may pay %G %R %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Saber Tiger, Phantom Beast Deity");
        setDescription("en_fan",
                "=H 1 red LRIG\n\n" +
                "@C: All of your other SIGNI get +3000 power.\n" +
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]. Then, if 3 or more cards were moved from your deck to the ener zone this turn, target 1 of your opponent's SIGNI, and you may pay %G %R %X. If you do, and banish it."
        );

		setName("zh_simplified", "幻兽神 剑齿虎");
        setDescription("zh_simplified", 
                "=H红色的分身1只（当这只精灵出场时，如果不把你的竖直状态的红色的分身1只横置，那么将此牌横置）\n" +
                "@C :你的其他的精灵的力量+3000。\n" +
                "@U :当这只精灵攻击时，[[能量填充1]]。然后，这个回合从你的牌组把牌3张以上往能量区移动过的场合，可以支付%G%R%X。这样做的场合，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.RED)));

            registerConstantAbility(new TargetFilter().own().SIGNI().except(cardId), new PowerModifier(3000));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            enerCharge(1);

            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENER && isOwnCard(event.getCaller()) &&
                event.getCaller().isEffectivelyAtLocation(CardLocation.DECK_MAIN)) >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
