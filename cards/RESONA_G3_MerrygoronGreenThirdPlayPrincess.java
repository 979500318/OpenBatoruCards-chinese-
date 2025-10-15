package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class RESONA_G3_MerrygoronGreenThirdPlayPrincess extends Card {

    public RESONA_G3_MerrygoronGreenThirdPlayPrincess()
    {
        setImageSets("WXDi-P11-TK03");

        setOriginalName("緑参ノ遊姫　メリゴラン");
        setAltNames("リョクサンノユウキメリゴラン Ryokusan no Yuuki Merigoran");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計２枚トラッシュに置く\n\n" +
                "@U：このシグニがアタックしたとき、あなたのエナゾーンからカードを１枚まで対象とし、それを手札に加える。\n" +
                "@U：このシグニが場を離れたとき、あなたのエナゾーンからカードを１枚まで対象とし、それを手札に加える。\n" +
                "@E：【エナチャージ２】"
        );

        setName("en", "Merigoran, Green Extreme Play");
        setDescription("en",
                "Put two SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@U: Whenever this SIGNI attacks, add up to one target card from your Ener Zone to your hand.\n" +
                "@U: When this SIGNI leaves the field, add up to one target card from your Ener Zone to your hand.\n" +
                "@E: [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Merrygoron, Green Third Play Princess");
        setDescription("en_fan",
                "Put 2 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U: Whenever this SIGNI attacks, target up to 1 card from your ener zone, and add it to your hand.\n" +
                "@U: When this SIGNI leaves the field, target up 1 card from your ener zone, and add it to your hand.\n" +
                "@E: [[Ener Charge 2]]"
        );
        
		setName("zh_simplified", "绿叁之游姬 旋转木马");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计2张放置到废弃区\n" +
                "@U :当这只精灵攻击时，从你的能量区把牌1张最多作为对象，将其加入手牌。\n" +
                "@U :当这只精灵离场时，从你的能量区把牌1张最多作为对象，将其加入手牌。\n" +
                "@E :[[能量填充2]]\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 2, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto2.setCondition(this::onAutoEff2Cond);

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEff2Cond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(0, 1, new TargetFilter(TargetHint.HAND).own().fromEner()).get();
            addToHand(target);
        }

        private void onEnterEff()
        {
            enerCharge(2);
        }
    }
}

