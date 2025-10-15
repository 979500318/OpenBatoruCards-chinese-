package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_MidorikoFessonePhantomBeast extends Card {

    public SIGNI_G1_MidorikoFessonePhantomBeast()
    {
        setImageSets("WXDi-P14-063");

        setOriginalName("幻獣　緑子//フェゾーネ");
        setAltNames("ゲンジュウミドリコフェゾーネ Genjuu Midoriko Fezoone");
        setDescription("jp",
                "@C：あなたのエナゾーンに緑のカードが３枚以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@U：このシグニがアタックしたとき、各プレイヤーは【エナチャージ１】をする。"
        );

        setName("en", "Midoriko//Fesonne, Phantom Beast");
        setDescription("en",
                "@C: As long as there are three or more green cards in your Ener Zone, this SIGNI gets +4000 power.\n@U: Whenever this SIGNI attacks, each player [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Midoriko//Fessone, Phantom Beast");
        setDescription("en_fan",
                "@C: As long as there are 3 or more green cards in your ener zone, this SIGNI gets +4000 power.\n" +
                "@U: Whenever this SIGNI attacks, each player [[Ener Charge 1]]."
        );

		setName("zh_simplified", "幻兽 绿子//音乐节");
        setDescription("zh_simplified", 
                "@C :你的能量区的绿色的牌在3张以上时，这只精灵的力量+4000。\n" +
                "@U :当这只精灵攻击时，各玩家[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withColor(CardColor.GREEN).fromEner().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onAutoEff()
        {
            enerCharge(1);
            enerCharge(getOpponent(), 1);
        }
    }
}
