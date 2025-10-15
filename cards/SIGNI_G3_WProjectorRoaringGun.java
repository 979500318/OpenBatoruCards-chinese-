package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G3_WProjectorRoaringGun extends Card {

    public SIGNI_G3_WProjectorRoaringGun()
    {
        setImageSets("SPDi43-19");
        setLinkedImageSets("SPDi43-13");

        setOriginalName("轟砲　Wプロジェクター");
        setAltNames("ゴウホウダバリュープロジェクター Gouhou Dabaryuu Purojekutaa");
        setDescription("jp",
                "@U $T1：あなたの《VJ.WOLF 3rdVerse-ULT》がダウンしたとき、あなたの場にあるシグニのパワーの合計が30000以上の場合、【エナチャージ１】をする。\n" +
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、あなたのルリグ１体を対象とし、%Gを支払ってもよい。そうした場合、それをアップする。"
        );

        setName("en", "W Projector, Roaring Gun");
        setDescription("en",
                "@U $T1: When your \"VJ.WOLF 3rd Verse-ULT\" is downed, if the total power of all of your SIGNI is 30000 or more, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI banishes a SIGNI in battle, target 1 of your LRIG, and you may pay %G. If you do, up it."
        );

		setName("zh_simplified", "轰炮 W投影");
        setDescription("zh_simplified", 
                "@U $T1 :当你的《VJ.WOLF3rdVerse-ULT》#D时，你的场上的精灵的力量的合计在30000以上的场合，[[能量填充1]]。\n" +
                "@U :当这只精灵因为战斗把精灵1只破坏时，你的分身1只作为对象，可以支付%G。这样做的场合，将其竖直。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.DOWN, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getName().getValue().contains("VJ.WOLF 3rdVerse-ULT") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).sum() >= 30000)
            {
                enerCharge(1);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UP).own().anyLRIG()).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 1)))
            {
                up(target);
            }
        }
    }
}
